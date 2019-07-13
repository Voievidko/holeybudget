package com.notspend.service;

import com.notspend.entity.Account;

import java.util.List;

public interface AccountService {

    void addAccount(Account account);
    List<Account> getAccounts();
    Account getAccount(int id);
    void deleteAccountById(int id);
    void updateAccount(Account account);

    boolean isAccountHaveRelations(int id);
    int replaceAccountInAllExpenses(int fromAccountId, int toAccountId);
    boolean transferMoneyBetweenAccounts(int fromAccountId, int toAccountId, Double sum);
    boolean transferMoneyBetweenAccountsWithDifferentCurrency(int fromAccountId, int toAccountId, Double from, Double to);
}
