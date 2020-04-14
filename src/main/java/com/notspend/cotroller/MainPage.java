package com.notspend.cotroller;

import com.notspend.entity.Account;
import com.notspend.entity.Category;
import com.notspend.entity.Currency;
import com.notspend.entity.Expense;
import com.notspend.entity.User;
import com.notspend.service.CategoryService;
import com.notspend.service.ExpenseService;
import com.notspend.service.ExpenseSyncService;
import com.notspend.service.UserService;
import com.notspend.util.CalculationHelper;
import com.notspend.util.SecurityUserHandler;
import com.notspend.util.CurrencyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainPage {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseSyncService expenseSyncService;

    @GetMapping(value = "/")
    public String getMainPage(HttpServletRequest request){
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);
        List<Account> accountList = user.getAccounts();
        List<Account> accountsToSync = accountList.stream().filter(a -> a.getToken() != null).collect(Collectors.toList());
        if (!accountsToSync.isEmpty()){
            try {
                expenseSyncService.syncDataWithBankServer(accountsToSync);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("totalSum", String.format("%.2f", CalculationHelper.accountSum(accountList)));

        List<Expense> expenseDuringCurrentMonth = expenseService.getExpensesDuringCurrentMonth();
        List<Expense> incomeDuringCurrentMonth = expenseService.getIncomesDuringCurrentMonth();

        request.getSession().setAttribute("spendCurrentMonth", String.format("%.2f", CalculationHelper.expenseSum(expenseDuringCurrentMonth)));
        request.getSession().setAttribute("earnCurrentMonth", String.format("%.2f", CalculationHelper.expenseSum(incomeDuringCurrentMonth)));

        //Data for year income graph
        List<Expense> incomeDuringLastYear = expenseService.getAllIncomeDuringYear();
        List<Double> incomeSumForEachMonth = new ArrayList<>();
        List<String> monthNames = new ArrayList<>();
        int monthFrom = LocalDate.now().getMonthValue() + 1;
        for (int i = monthFrom; i < monthFrom + 12; i++) {
            Double monthSum = 0d;
            int currentMonthNumber = getRightMonthOrder(i);
            for (Expense expense : incomeDuringLastYear) {
                if ((expense.getDate().getMonthValue()) == currentMonthNumber) {
                    monthSum += expense.getCurrency().getCode().equals("UAH") ? expense.getSum() : expense.getSum() * CurrencyProcessor.getCurrencyRateToUah(expense.getCurrency().getCode());
                }
            }
            incomeSumForEachMonth.add(monthSum);
            String monthName = Month.of(currentMonthNumber).name();
            monthNames.add(monthName.substring(0,3));
        }
        String income = incomeSumForEachMonth.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
        String months = monthNames.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");

        request.getSession().setAttribute("income", income);
        request.getSession().setAttribute("months", months);

        //Data for current month
        List<Expense> expensesThisMonth = expenseService.getExpensesDuringCurrentMonth();
        List<Double> expenseSumForEachDay = new ArrayList<>();
        List<String> dayNames = new ArrayList<>();
        for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++){
            Double daySum = 0d;
            for (Expense expense : expensesThisMonth) {
                if ((expense.getDate().getDayOfMonth()) == i) {
                    daySum += expense.getCurrency().getCode().equals("UAH") ? expense.getSum() : expense.getSum() * CurrencyProcessor.getCurrencyRateToUah(expense.getCurrency().getCode());
                }
            }
            expenseSumForEachDay.add(daySum);
            dayNames.add(String.valueOf(i));
        }
        String expensePerDay = expenseSumForEachDay.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
        String days = dayNames.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");

        request.getSession().setAttribute("expensePerDay", expensePerDay);
        request.getSession().setAttribute("days", days);

        //Group monthExpenseByCategory
        List<Category> categories = categoryService.getAllExpenseCategories();
        List<Double> expenseSumForEachCategory = new ArrayList<>();
        List<String> categoryNames = new ArrayList<>();
        for(Category category : categories){
            Double sumByCategory = 0d;
            for(Expense expense : expensesThisMonth){
                if(expense.getCategory().getName().equals(category.getName())){
                    sumByCategory += expense.getCurrency().getCode().equals("UAH") ? expense.getSum() : expense.getSum() * CurrencyProcessor.getCurrencyRateToUah(expense.getCurrency().getCode());
                }
            }
            expenseSumForEachCategory.add(sumByCategory);
            categoryNames.add(category.getName());
        }

        String expenseSumForEachCategoryStr = expenseSumForEachCategory.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
        String categoryNamesStr = categoryNames.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");

        request.getSession().setAttribute("expenseSumForEachCategory", expenseSumForEachCategoryStr);
        request.getSession().setAttribute("categoryNames", categoryNamesStr);

        Map<String,Double> currencyValues = accountList.stream()
                .map(Account::getCurrency)
                .distinct()
                .collect(Collectors.toMap(Currency::getCode, b -> CurrencyProcessor.getCurrencyRateToUah(b.getCode())));

        request.getSession().setAttribute("currencyValues", currencyValues);
        return "index";
    }

    private int getRightMonthOrder(int monthNumber){
        int month = monthNumber % 12;
        return month == 0 ? 12 : month;
    }
}
