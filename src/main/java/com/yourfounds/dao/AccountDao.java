package com.yourfounds.dao;

import com.yourfounds.entity.Account;

public interface AccountDao extends CrudDao<Account,Integer> {
    boolean isAccountHaveRelations(Integer id);
    int replaceAccountInAllExpenses(Integer fromAccountId, Integer toAccountId);
}
