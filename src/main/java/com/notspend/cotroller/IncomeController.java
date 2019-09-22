package com.notspend.cotroller;

import com.notspend.entity.Expense;
import com.notspend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("income")
public class IncomeController {

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping("currentmonth")
    public String showIncomeForLastMonth(Model model){
        List<Expense> currentYearIncome = expenseService.getIncomesDuringCurrentMonth();
        model.addAttribute("currentYearIncome", currentYearIncome);
        return "income/year";
    }

    @RequestMapping("year")
    public String showIncomeForLastYear(Model model){
        List<Expense> currentYearIncome = expenseService.getAllIncomeDuringYear();
        model.addAttribute("currentYearIncome", currentYearIncome);
        return "income/year";
    }
}
