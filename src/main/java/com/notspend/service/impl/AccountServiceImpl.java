package com.notspend.service.impl;

import com.notspend.dao.AccountDao;
import com.notspend.entity.Account;
import com.notspend.service.AccountService;
import com.notspend.service.ExpenseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public void addAccount(Account account) {
        accountDao.save(account);
    }

    @Override
    @Transactional
    public List<Account> getAccounts() {
        return accountDao.getAll();
    }

    @Override
    @Transactional
    public Account getAccount(int id) {
        return accountDao.get(id);
    }

    @Override
    @Transactional
    public void deleteAccountById(int id) {
        accountDao.delete(id);
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    @Override
    @Transactional
    public boolean isAccountHaveRelations(int id) {
        return accountDao.isAccountHaveRelations(id);
    }

    @Override
    @Transactional
    public int replaceAccountInAllExpenses(int fromAccountId, int toAccountId) {
        int numberOfReplaced = accountDao.replaceAccountInAllExpenses(fromAccountId, toAccountId);
        Account accountToDelete = accountDao.get(fromAccountId);
        Account accountToUpdate = accountDao.get(toAccountId);
        accountToUpdate.plus(accountToDelete.getSummary());
        accountDao.update(accountToUpdate);
        accountDao.delete(accountToDelete.getAccountId());

        return numberOfReplaced;
    }

    @Override
    @Transactional
    public boolean transferMoneyBetweenAccounts(int fromAccountId, int toAccountId, Double sum) {
        Account accountFrom = accountDao.get(fromAccountId);
        Account accountTo = accountDao.get(toAccountId);
        accountFrom.substract(sum);
        accountTo.plus(sum);
        accountDao.update(accountFrom);
        accountDao.update(accountTo);
        return true;
    }

    @Override
    @Transactional
    public boolean transferMoneyBetweenAccountsWithDifferentCurrency(int fromAccountId, int toAccountId, Double from, Double to) {
        Account accountFrom = accountDao.get(fromAccountId);
        Account accountTo = accountDao.get(toAccountId);
        accountFrom.substract(from);
        accountTo.plus(to);
        accountDao.update(accountFrom);
        accountDao.update(accountTo);
        return true;
    }
}
