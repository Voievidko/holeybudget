package com.yourfounds.cotroller;

import com.yourfounds.entity.Currency;
import com.yourfounds.entity.User;
import com.yourfounds.service.CurrencyService;
import com.yourfounds.service.UserService;
import com.yourfounds.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("currency")
public class CurrencyController {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("all")
    public String listCurrency(Model model){
        List<Currency> currencies = currencyService.getAllCurrenciesAssignedToUser();
        model.addAttribute("currencies", currencies);
        return "currency/all";
    }
}
