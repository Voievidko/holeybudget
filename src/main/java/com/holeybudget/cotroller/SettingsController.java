package com.holeybudget.cotroller;

import com.holeybudget.entity.*;
import com.holeybudget.service.CurrencyService;
import com.holeybudget.service.UserService;
import com.holeybudget.util.SecurityUserHandler;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class SettingsController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "settings")
    public String listSettings(Model model){
        List<Currency> currencies = currencyService.getAllCurrenciesAssignedToUser();
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);
        Currency currency = user.getDefaultCurrency();
        if (currency != null){
            currencies.sort((c1, c2) -> {
                if (c1.getCode().equals(currency.getCode())) return -1;
                if (c2.getCode().equals(currency.getCode())) return 1;
                return c1.getCode().compareTo(c2.getCode());
            });
        }
        model.addAttribute("currencies", currencies);
        return "settings/settings";
    }

    @PostMapping(path = "saveSettings")
    public String saveSettings(@ModelAttribute("tempCurrency") Currency currency,
                               Model model){
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);
        Currency defaultCurrency = currencyService.getCurrencyByCode(currency.getCode());
        user.setDefaultCurrency(defaultCurrency);
        userService.updateUser(user);
        return "redirect:/";
    }
}
