package com.yourfounds.service.impl;

import com.yourfounds.dao.AccountDao;
import com.yourfounds.entity.Account;
import com.yourfounds.service.AccountService;
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
}
