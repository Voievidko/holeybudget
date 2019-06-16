package com.yourfounds.cotroller;

import com.yourfounds.entity.Account;
import com.yourfounds.entity.Category;
import com.yourfounds.entity.Expense;
import com.yourfounds.entity.User;
import com.yourfounds.service.AccountService;
import com.yourfounds.service.CategoryService;
import com.yourfounds.service.ExpenseService;
import com.yourfounds.util.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("expense")
public class ExpenseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private AccountService accountService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("add")
    public String addForm(Model model){
        Expense expense = new Expense();

        List<Category> categories = categoryService.getCategories();
        List<Account> accounts = accountService.getAccounts();

        //insert today's date
        expense.setDate(new Date());

        model.addAttribute("categories", categories);
        model.addAttribute("expense", expense);
        model.addAttribute("accounts", accounts);

        return "expense/add";
    }

    //Annotation Valid from javax Validation will check data
    //Validation annotation should be in POJO class like @NotNull, @Min
    @RequestMapping("addProcess")
    public String processForm(@Valid @ModelAttribute("expense") Expense expense, BindingResult bindingResult,
                              @ModelAttribute("tempCategory") Category category, @ModelAttribute("tempAccount") Account account,
                              Model model){
        if(bindingResult.hasErrors()){
            List<Category> categories = categoryService.getCategories();
            List<Account> accounts = accountService.getAccounts();
            expense.setDate(new Date());
            model.addAttribute("categories", categories);
            model.addAttribute("accounts", accounts);
            return "expense/add";
        }
        //todo: User is hardcoded. Replace it when implement needed logic.
        User user = new User();
        user.setUserId(1);
        expense.setCategory(categoryService.getCategory(category.getCategoryId()));
        expense.setAccount(accountService.getAccount(account.getAccountId()));
        expense.setTime(new Date());
        expense.setUser(user);
        expenseService.addExpense(expense);
        return "redirect:add";
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
        model.addAttribute("totalSum", Calculation.exspenseSum(expenses));
        return "expense/all";
    }

    @RequestMapping("currentmonth")
    public String showCurrentMonth(Model model){
        List<Expense> expenses = expenseService.getExpensesDuringCurrentMonth();
        model.addAttribute("expenseName", "Expenses during " + expenseService.getCurrentMonthName());
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSum", Calculation.exspenseSum(expenses));
        return "expense/all";
    }

}
