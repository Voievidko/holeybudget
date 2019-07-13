package com.notspend.dao;

import com.notspend.entity.Account;

public interface AccountDao extends CrudDao<Account,Integer> {
    boolean isAccountHaveRelations(Integer id);
    int replaceAccountInAllExpenses(Integer fromAccountId, Integer toAccountId);
}
