package com.notspend.cotroller;

import com.notspend.entity.Authority;
import com.notspend.entity.User;
import com.notspend.service.AuthorityService;
import com.notspend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping("all")
    public String showAll(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/all";
    }

    @RequestMapping("register")
    public String register(Model model){
        //logout if user login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            authentication.setAuthenticated(false);
        }

        User user = new User();
        model.addAttribute("user", user);
        return "user/register";
    }

    @RequestMapping("registerprocess")
    public String registerProcess(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                                  @ModelAttribute("passwordSecondTime") String passwordSecondTime, Model model){
        if (bindingResult.hasErrors()){
            return "user/register";
        }
        if (userService.getUser(user.getUsername()) != null){
            model.addAttribute("userexist", "User with username " + user.getUsername() + " is already register");
            return "user/register";
        }
        if (!user.getPassword().equals(passwordSecondTime)){
            return "redirect:register";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEnabled(true);
        userService.addUser(user);

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUsername(user);
        authorityService.saveAuthority(authority);
        return "/login";
    }

    @RequestMapping("forgetpassword")
    public String forgetPassword(){
        return "user/forgetpassword";
    }
}
