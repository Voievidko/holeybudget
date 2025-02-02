package com.holeybudget.cotroller;

import com.holeybudget.entity.Account;
import com.holeybudget.entity.Category;
import com.holeybudget.entity.Currency;
import com.holeybudget.entity.Expense;
import com.holeybudget.entity.User;
import com.holeybudget.exception.AccountSyncFailedException;
import com.holeybudget.service.*;
import com.holeybudget.util.CalculationHelper;
import com.holeybudget.util.CurrencyProcessor;
import com.holeybudget.util.SecurityUserHandler;
import java.util.Comparator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@CommonsLog
public class MainPage {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseSyncService expenseSyncService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "/")
    public String getMainPage(HttpServletRequest request){
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);
        Currency userCurrency = user.getDefaultCurrency();
        if (userCurrency == null){
            userCurrency = currencyService.getCurrencyByCode("USD");
            user.setDefaultCurrency(userCurrency);
            userService.updateUser(user);
        }
        final Currency defaultCurrency = userCurrency;
        List<Account> accountList = user.getAccounts();
        List<Account> accountsToSync = accountList.stream()
                .filter(a -> a.getToken() != null && !a.getToken().isEmpty())
                .collect(Collectors.toList());
        if (!accountsToSync.isEmpty()){
            log.debug("Start syncing accounts. Account to sync size: " + accountsToSync.size());
            try {
                expenseSyncService.syncDataWithBankServer(accountsToSync);
            } catch (AccountSyncFailedException e) {
                log.error("Can't synchronize accounts." + e);
            }
            log.debug("Finish syncing accounts. ");
        }

        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("totalSum", String.format("%.2f", CalculationHelper.accountSum(accountList, defaultCurrency)));

        List<Expense> expenseDuringCurrentMonth = expenseService.getExpensesDuringCurrentMonth();
        List<Expense> incomeDuringCurrentMonth = expenseService.getIncomesDuringCurrentMonth();

        request.getSession().setAttribute("spendCurrentMonth", String.format("%.2f", CalculationHelper.expenseSum(expenseDuringCurrentMonth, defaultCurrency)));
        request.getSession().setAttribute("earnCurrentMonth", String.format("%.2f", CalculationHelper.expenseSum(incomeDuringCurrentMonth, defaultCurrency)));

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
                    monthSum += expense.getCurrency().getCode().equals(defaultCurrency.getCode()) ? expense.getSum() : expense.getSum() * CurrencyProcessor.getCurrencyRate(expense.getCurrency().getCode(), defaultCurrency.getCode());
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
                    daySum += expense.getCurrency().getCode().equals(defaultCurrency.getCode()) ? expense.getSum() : expense.getSum() * CurrencyProcessor.getCurrencyRate(expense.getCurrency().getCode(), defaultCurrency.getCode());
                }
            }
            expenseSumForEachDay.add(daySum);
            dayNames.add(String.valueOf(i));
        }
        String expensePerDay = expenseSumForEachDay.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
        String days = dayNames.toString().replaceFirst("\\[", "").replaceFirst("\\]", "");

        request.getSession().setAttribute("expensePerDay", expensePerDay);
        request.getSession().setAttribute("days", days);
        request.getSession().setAttribute("defaultCurrency", defaultCurrency.getCode());

        //Group monthExpenseByCategory
        List<Category> categories = categoryService.getAllExpenseCategories();
        List<Double> expenseSumForEachCategory = new ArrayList<>();
        List<String> categoryNames = new ArrayList<>();
        for(Category category : categories){
            Double sumByCategory = 0d;
            for(Expense expense : expensesThisMonth){
                if(expense.getCategory().getName().equals(category.getName())){
                    sumByCategory += expense.getCurrency().getCode().equals(defaultCurrency.getCode()) ? expense.getSum() : expense.getSum() * CurrencyProcessor.getCurrencyRate(expense.getCurrency().getCode(), defaultCurrency.getCode());
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


        //Top month expenses
        List<Expense> topMonthExpenses = expensesThisMonth.stream()
            .sorted((o1, o2) -> {
                    double o1currency = CurrencyProcessor.getCurrencyRate(o1.getCurrency().getCode(), defaultCurrency.getCode());
                    double o2currency = CurrencyProcessor.getCurrencyRate(o2.getCurrency().getCode(), defaultCurrency.getCode());
                    if (o1.getSum() * o1currency < o2.getSum() * o2currency) return 1;
                    else if (o1.getSum() * o1currency > o2.getSum() * o2currency) return -1;
                    else return 0;
                }
            )
            .limit(5)
            .collect(Collectors.toList());
        request.getSession().setAttribute("topMonthExpenses", topMonthExpenses);

        //Today
        List<Expense> today = expensesThisMonth.stream()
            .filter(e -> e.getDate().getYear() == LocalDate.now().getYear())
            .filter(e -> e.getDate().getMonthValue() == LocalDate.now().getMonthValue())
            .filter(e -> e.getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth())
            .sorted((o1, o2) -> {
                    double o1currency = CurrencyProcessor.getCurrencyRate(o1.getCurrency().getCode(), defaultCurrency.getCode());
                    double o2currency = CurrencyProcessor.getCurrencyRate(o2.getCurrency().getCode(), defaultCurrency.getCode());
                    if (o1.getSum() * o1currency < o2.getSum() * o2currency) return 1;
                    else if (o1.getSum() * o1currency > o2.getSum() * o2currency) return -1;
                    else return 0;
                }
            )
            .limit(5)
            .collect(Collectors.toList());

        request.getSession().setAttribute("topTodayExpenses", today);


        //Year
        List<Expense> year = expenseService.getAllExpenseDuringYear().stream()
            .filter(e -> e.getDate().getYear() == LocalDate.now().getYear())
            .sorted((o1, o2) -> {
                    double o1currency = CurrencyProcessor.getCurrencyRate(o1.getCurrency().getCode(), defaultCurrency.getCode());
                    double o2currency = CurrencyProcessor.getCurrencyRate(o2.getCurrency().getCode(), defaultCurrency.getCode());
                    if (o1.getSum() * o1currency < o2.getSum() * o2currency) return 1;
                    else if (o1.getSum() * o1currency > o2.getSum() * o2currency) return -1;
                    else return 0;
                }
            )
            .limit(5)
            .collect(Collectors.toList());

        request.getSession().setAttribute("topYearExpenses", year);

        return "index";
    }

    private int getRightMonthOrder(int monthNumber){
        int month = monthNumber % 12;
        return month == 0 ? 12 : month;
    }
}
