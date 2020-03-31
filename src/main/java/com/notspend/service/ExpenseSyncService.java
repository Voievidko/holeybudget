package com.notspend.service;

import com.notspend.entity.Account;

import java.util.List;

public interface ExpenseSyncService {
    void syncDataWithBankServer(List<Account> accounts) throws Exception;
}
