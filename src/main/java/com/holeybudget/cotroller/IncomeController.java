package com.holeybudget.cotroller;

import com.holeybudget.entity.Expense;
import com.holeybudget.entity.Profit;
import com.holeybudget.entity.User;
import com.holeybudget.service.ExpenseService;
import com.holeybudget.service.ProfitService;
import com.holeybudget.service.UserService;
import com.holeybudget.util.SecurityUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("income")
public class IncomeController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ProfitService profitService;

    @Autowired
    private UserService userService;

    @GetMapping("currentmonth")
    public String showIncomeForLastMonth(Model model){
        List<Expense> currentYearIncome = expenseService.getIncomesDuringCurrentMonth();
        model.addAttribute("currentYearIncome", currentYearIncome);
        return "income/year";
    }

    @GetMapping("year")
    public String showIncomeForLastYear(Model model){
        List<Expense> currentYearIncome = expenseService.getAllIncomeDuringYear();
        model.addAttribute("currentYearIncome", currentYearIncome);
        return "income/year";
    }

    @GetMapping("profit")
    public String showProfitForLastYear(Model model){
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);
        String defaultCurrencyCode = user.getDefaultCurrency().getCode();
        List<Expense> currentYearIncome = expenseService.getAllIncomeDuringYear();
        List<Expense> currentYearExpense = expenseService.getAllExpenseDuringYear();
        List<Profit> profit = profitService.getProfitTableDuringLastYear(currentYearIncome, currentYearExpense, defaultCurrencyCode);
        model.addAttribute("profit", profit);
        return "income/profit";
    }
}
