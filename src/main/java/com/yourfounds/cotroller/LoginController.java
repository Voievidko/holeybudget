package com.yourfounds.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("login")
    public String loginPage(){
        return "fancy-login";
    }

    @GetMapping("access-denied")
    public String showAccessDenied(){
        return "access-denied";
    }
}
