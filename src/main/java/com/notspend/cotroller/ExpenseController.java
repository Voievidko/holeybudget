package com.notspend.cotroller;

import com.notspend.entity.*;
import com.notspend.service.*;
import com.notspend.util.CalculationHelper;
import com.notspend.util.DateHelper;
import com.notspend.util.SecurityUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("expense")
public class ExpenseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserService userService;

    @RequestMapping("add")
    public String addForm(Model model){
        Expense expense = new Expense();

        if (!model.containsAttribute("categories")){
            List<Category> categories = categoryService.getAllExpenseCategories();
            model.addAttribute("categories", categories);
        }

        if (!model.containsAttribute("accounts")){
            List<Account> accounts = accountService.getAccounts();
            model.addAttribute("accounts", accounts);
        }

        if (!model.containsAttribute("currencies")){
            List<Currency> currencies = currencyService.getAllCurrenciesAssignedToUser();
            model.addAttribute("currencies", currencies);
        }

        //insert today's date
        expense.setDate(LocalDate.now());
        model.addAttribute("expense", expense);
        model.addAttribute("type", "Expense");
        return "expense/add";
    }

    //Annotation Valid from javax Validation will check data
    //Validation annotation should be in POJO class like @NotNull, @Min
    @RequestMapping("addProcess")
    public String processForm(@Valid @ModelAttribute("expense") Expense expense, BindingResult bindingResult,
                              @ModelAttribute("tempCategory") Category category,
                              @ModelAttribute("tempAccount") Account account,
                              @ModelAttribute("tempCurrency") Currency currency,
                              RedirectAttributes redirectAttributes,
                              Model model){
        if(bindingResult.hasErrors()){
            List<Category> categories;
            List<Currency> currencies = currencyService.getAllCurrenciesAssignedToUser();
            if(category.isIncome()){
                categories = categoryService.getAllIncomeCategories();
            } else {
                categories = categoryService.getAllExpenseCategories();

            }
            List<Account> accounts = accountService.getAccounts();
            expense.setDate(LocalDate.now());
            model.addAttribute("categories", categories);
            model.addAttribute("accounts", accounts);
            model.addAttribute("currencies", currencies);
            return "expense/add";
        }

        User user = userService.getUser(SecurityUserHandler.getCurrentUser());
        Category selectedCategory = categoryService.getCategory(category.getCategoryId());
        Currency selectedCurrency = currencyService.getCurrencyByCode(currency.getCode());
        Account selectedAccount = accountService.getAccount(account.getAccountId());

        expense.setCurrency(selectedCurrency);
        expense.setCategory(selectedCategory);
        expense.setAccount(selectedAccount);
        expense.setTime(LocalTime.now());
        expense.setUser(user);

        if(expense.getCurrency().equals(expense.getAccount().getCurrency())){
            expenseService.addExpense(expense);
            if (expense.getCategory().isIncome()) {
                return "redirect:/";
            } else {
                List<Category> categoriesForRedirection = categoryService.getAllExpenseCategories();
                List<Currency> currenciesForRedirection = currencyService.getAllCurrenciesAssignedToUser();
                List<Account> accountsForRedirection = accountService.getAccounts();

                //Make selected element first in list
                categoriesForRedirection.remove(selectedCategory);
                categoriesForRedirection.add(0, selectedCategory);

                currenciesForRedirection.remove(selectedCurrency);
                currenciesForRedirection.add(0, selectedCurrency);

                accountsForRedirection.remove(selectedAccount);
                accountsForRedirection.add(0, selectedAccount);

                redirectAttributes.addFlashAttribute("categories", categoriesForRedirection);
                redirectAttributes.addFlashAttribute("accounts", accountsForRedirection);
                redirectAttributes.addFlashAttribute("currencies", currenciesForRedirection);
                return "redirect:add";
            }
        } else {
            model.addAttribute("expense", expense);
            return "expense/setcurrency";
        }
    }

    @RequestMapping("currencyProcess")
    public String currencyProcess(@ModelAttribute("currency") Double currency,
                                  @ModelAttribute("expenseSum") String expenseSum,
                                  @ModelAttribute("expenseDate") String expenseDate,
                                  @ModelAttribute("expenseTime") String expenseTime,
                                  @ModelAttribute("expenseComment") String expenseComment,
                                  @ModelAttribute("expenseCategory") String expenseCategory,
                                  @ModelAttribute("expenseAccount") String expenseAccount,
                                  @ModelAttribute("expenseCurrency") String expenseCurrency,
                                  Model model){
        Expense expense = new Expense();
        expense.setDate(LocalDate.parse(expenseDate));
        expense.setTime(LocalTime.now());
        expense.setComment(expenseComment);
        Category category = categoryService.getCategory(Integer.parseInt(expenseCategory));
        Account account = accountService.getAccount(Integer.parseInt(expenseAccount));
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);

        expense.setCategory(category);
        expense.setAccount(account);
        expense.setUser(user);
        expense.setCurrency(account.getCurrency());
        expense.setSum(currency);

        expenseService.addExpense(expense);

        if (expense.getCategory().isIncome()) {
            return "redirect:/";
        } else {
            return "redirect:add";
        }
    }

    @RequestMapping("income")
    public String addIncome(Model model){
        Expense expense = new Expense();

        List<Category> categories = categoryService.getAllIncomeCategories();
        List<Account> accounts = accountService.getAccounts();

        //insert today's date
        expense.setDate(LocalDate.now());
        List<Currency> currencies = currencyService.getAllCurrenciesAssignedToUser();

        model.addAttribute("currencies", currencies);
        model.addAttribute("categories", categories);
        model.addAttribute("expense", expense);
        model.addAttribute("accounts", accounts);
        model.addAttribute("type","income");

        return "expense/add";
    }

    @RequestMapping("delete")
    public String deleteExpense(@ModelAttribute("expenseId") int expenseId){
        expenseService.deleteExpenseById(expenseId);
        return "success";
    }

    @RequestMapping("all")
    public String showAll(Model model){
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenseName", "Expenses during all time");
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSum", CalculationHelper.expenseSum(expenses));

        //for sums
        List<Account> accounts = accountService.getAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("allMoneySummary", CalculationHelper.accountSum(accounts));
        return "expense/all";
    }

    @RequestMapping("currentmonth")
    public String showCurrentMonth(Model model){
        List<Expense> expenses = expenseService.getExpensesDuringCurrentMonth();
        model.addAttribute("expenseName", "Expenses during " + expenseService.getCurrentMonthName());
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSum", CalculationHelper.expenseSum(expenses));

        //for sums
        List<Account> accounts = accountService.getAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("allMoneySummary", CalculationHelper.accountSum(accounts));
        return "expense/all";
    }

    @RequestMapping("year")
    public String showExpenseForLastYear(Model model){
        List<Expense> currentYearExpense = expenseService.getAllExpenseDuringYear();
        model.addAttribute("currentYearExpense", transform(currentYearExpense));
        return "expense/year";
    }

    private Map<String, Double> transform(List<Expense> expenses){
        Map<String, Double> map = new HashMap<>();
        for(Expense expense : expenses){
            LocalDate localDate = expense.getDate();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            String key = year + "-" + month;
            map.putIfAbsent(key, 0D);
            map.put(key, map.get(key) + expense.getSum());
        }
        return map;
    }
}
