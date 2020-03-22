package com.notspend.cotroller;

import com.notspend.entity.Account;
import com.notspend.entity.Currency;
import com.notspend.entity.User;
import com.notspend.service.AccountService;
import com.notspend.service.CurrencyService;
import com.notspend.service.UserService;
import com.notspend.util.CalculationHelper;
import com.notspend.util.SecurityUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("add")
    public String addAccount(Model model){
        Account account = new Account();
        List<Currency> currencies = currencyService.getAllCurrencies();
        model.addAttribute("account", account);
        model.addAttribute("currencies", currencies);
        return "account/add";
    }

    @RequestMapping("addProcess")
    public String processAddAccountForm(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult,
                                        @ModelAttribute("tempCurrency") Currency currency,
                                        Model model){
        if (bindingResult.hasErrors()){
            List<Currency> currencies = currencyService.getAllCurrencies();
            model.addAttribute("currencies", currencies);
            return "account/add";
        }
        User user = userService.getUser(SecurityUserHandler.getCurrentUser());
        account.setUser(user);
        account.setCurrency(currencyService.getCurrencyByCode(currency.getCode()));
        accountService.addAccount(account);
        return "success";
    }

    @GetMapping("all")
    public String listAccounts (Model model){
        List<Account> accounts = accountService.getAccounts();

        model.addAttribute("accounts", accounts);
        return "account/all";
    }

    @GetMapping("update")
    public String updateAccount(@ModelAttribute("accountId") int accountId, Model model){
        Account account = accountService.getAccount(accountId);
        model.addAttribute("account", account);
        return "account/update";
    }

    @RequestMapping("updateprocess")
    public String updateProcess(@ModelAttribute("account") Account updatedAccount){
        //todo: rework this solution
        Account account = accountService.getAccount(updatedAccount.getAccountId());
        account.setType(updatedAccount.getType());
        account.setDescription(updatedAccount.getDescription());
        account.setToken(updatedAccount.getToken());
        accountService.updateAccount(account);
        return "redirect:all";
    }

    @RequestMapping("delete")
    public String deleteAccount(@ModelAttribute("accountId") int accountId, Model model){
        List<Account> accounts = accountService.getAccounts();

        //Check if this account is not last account
        if (accounts.size() <= 1){
            return "account/cantdeletelastaccount";
        }

        //inform User that account can't be deleted
        //and propose to transfer expenses to exist account
        Account accountToDelete = accountService.getAccount(accountId);
        accounts.remove(accountToDelete);

        Account account = new Account();
        model.addAttribute("accountToDelete", accountToDelete);
        model.addAttribute("accounts", accounts);
        model.addAttribute("account", account);
        return "account/cantdelete";

    }

    @RequestMapping("transferToExistAccount")
    public String transferToOtherAccountAndDelete(@ModelAttribute("accountId") int toAccountId,
                                                  @ModelAttribute("accountToDelete") int fromAccountId,
                                                  Model model){
        Account accountFrom = accountService.getAccount(fromAccountId);
        Account accountTo = accountService.getAccount(toAccountId);

        if (accountFrom.getCurrency().equals(accountTo.getCurrency())){
            accountService.replaceAccountInAllExpenses(fromAccountId, toAccountId);
            return "success";
        } else {
            model.addAttribute("account", accountFrom);
            model.addAttribute("accountFrom", accountFrom);
            model.addAttribute("accountTo", accountTo);
            model.addAttribute("sum", accountFrom.getSummary());
            model.addAttribute("delete", true);
            return "account/setcurrency";
        }
    }

    @RequestMapping("transfer")
    public String transferMoneyBetweenAccounts(Model model){
        List<Account> accounts = accountService.getAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("allMoneySummary", CalculationHelper.accountSum(accounts));
        return "account/transfer";
    }

    @RequestMapping("transferProcess")
    public String transferMoneyBetweenAccountsProcess(@ModelAttribute("accountFrom") int accountFromId,
                                                      @ModelAttribute("accountTo") int accountToId,
                                                      @ModelAttribute("sum") Double sum,
                                                      Model model){
        Account accountFrom = accountService.getAccount(accountFromId);
        Account accountTo = accountService.getAccount(accountToId);
        if (accountFrom.getCurrency().equals(accountTo.getCurrency())){
            accountService.transferMoneyBetweenAccounts(accountFromId, accountToId, sum);
            return "success";
        } else {
            model.addAttribute("account", accountFrom);
            model.addAttribute("accountFrom", accountFrom);
            model.addAttribute("accountTo", accountTo);
            model.addAttribute("sum", sum);
            model.addAttribute("delete", false);
            return "account/setcurrency";
        }
    }

    @RequestMapping("currencyProcess")
    public String transferMoneyBetweenAccountsWithDifferentCurrencyProcess(@ModelAttribute("accountFromId") int accountFromId,
                                                      @ModelAttribute("accountToId") int accountToId,
                                                      @ModelAttribute("sum") Double sum,
                                                      @ModelAttribute("currency") Double currency,
                                                      @ModelAttribute("delete") Boolean deleteAccount,
                                                      Model model){
        accountService.transferMoneyBetweenAccountsWithDifferentCurrency(accountFromId, accountToId, sum, currency);
        if (deleteAccount){
            accountService.replaceAccountInAllExpenses(accountFromId, accountToId);
        }
        return "success";
    }
}
