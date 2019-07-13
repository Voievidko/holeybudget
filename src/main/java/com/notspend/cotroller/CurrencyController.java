package com.notspend.cotroller;

import com.notspend.entity.Currency;
import com.notspend.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("all")
    public String listCurrency(Model model){
        List<Currency> currencies = currencyService.getAllCurrenciesAssignedToUser();
        model.addAttribute("currencies", currencies);
        return "currency/all";
    }
}
