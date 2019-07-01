package com.yourfounds.cotroller;

import com.yourfounds.entity.Account;
import com.yourfounds.entity.User;
import com.yourfounds.service.UserService;
import com.yourfounds.util.Calculation;
import com.yourfounds.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainPage {

//    @RequestMapping(value = "/")
//    public String getMainPage(HttpServletRequest request, Model model){
//        String username = Util.getCurrentUser();
//        model.addAttribute("username",username);
//        return "index";
//    }

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String getMainPage(HttpServletRequest request){
        String username = Util.getCurrentUser();
        User user = userService.getUser(username);
        List<Account> accountList = user.getAccounts();
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("totalSum", Calculation.accountSum(accountList));
        return "index";
    }
}
