package com.holeybudget.dao;

import com.holeybudget.entity.Account;

public interface AccountDao extends CrudDao<Account,Integer> {
    boolean isAccountHaveRelations(Integer id);
    int replaceAccountInAllExpenses(Integer fromAccountId, Integer toAccountId);
}
