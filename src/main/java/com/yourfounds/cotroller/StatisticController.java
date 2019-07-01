package com.yourfounds.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("statistic")
public class StatisticController {

    @RequestMapping("all")
    public String showAllStatistic(){
        return "statistic/all";
    }
}
