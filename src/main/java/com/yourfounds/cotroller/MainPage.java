package com.yourfounds.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPage {

    @RequestMapping(value = "/")
    public String getMainPage(){
        return "home";
    }
}
