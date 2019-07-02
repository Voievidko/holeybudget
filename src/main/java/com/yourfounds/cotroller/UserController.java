package com.yourfounds.cotroller;

import com.yourfounds.entity.Authority;
import com.yourfounds.entity.User;
import com.yourfounds.service.AuthorityService;
import com.yourfounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<User> users = userService.getUsers();
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
    public String registerProcess(@ModelAttribute("user") User user, @ModelAttribute("passwordSecondTime") String passwordSecondTime){
        // todo: very primitive for now
        // todo: check Security
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
