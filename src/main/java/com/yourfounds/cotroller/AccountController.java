package com.yourfounds.cotroller;

import com.yourfounds.entity.Account;
import com.yourfounds.entity.User;
import com.yourfounds.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("add")
    public String addAccount(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "account/add";
    }

    @RequestMapping("addProcess")
    public String processAddAccountForm(@ModelAttribute("account") Account account, Model model){
        //todo: User is hardcoded. Replace it when implement needed logic.
        User user = new User();
        user.setUserId(1);
        account.setUser(user);
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
        accountService.updateAccount(account);
        return "redirect:all";
    }

    @RequestMapping("delete")
    public String deleteAccount(@ModelAttribute("accountId") int accountId, Model model){
        //check if there is relationship on account
        if (accountService.isAccountHaveRelations(accountId)){
            //inform User that account can't be deleted
            //and propose to transfer expenses to exist account
            Account accountToDelete = accountService.getAccount(accountId);
            List<Account> accounts = accountService.getAccounts();
            accounts.remove(accountToDelete);

            Account account = new Account();
            model.addAttribute("accountToDelete", accountToDelete);
            model.addAttribute("accounts", accounts);
            model.addAttribute("account", account);
            return "account/cantdelete";
        } else {
            accountService.deleteAccountById(accountId);
            return "success";
        }
    }

    @RequestMapping("transferToExistAccount")
    public String transferToOtherAccountAndDelete(@ModelAttribute("accountId") int toAccountId, @ModelAttribute("accountToDelete") int fromAccountId, Model model){
        accountService.replaceAccountInAllExpenses(fromAccountId, toAccountId);
        return "success";
    }
}
