package com.holeybudget.service;

import com.holeybudget.entity.Account;
import com.holeybudget.exception.AccountSyncFailedException;

import java.util.List;

public interface ExpenseSyncService {
    void syncDataWithBankServer(List<Account> accounts) throws AccountSyncFailedException;
    void syncAccount(Account account) throws AccountSyncFailedException;
}
