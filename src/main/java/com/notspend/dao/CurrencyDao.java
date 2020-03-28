package com.notspend.dao;

import com.notspend.entity.Currency;
import com.notspend.entity.User;

import java.util.List;

public interface CurrencyDao extends CrudDao<Currency, String> {

    List<Currency> getAllCurrenciesAssignedToUser(User user);
    Currency get(Integer number);

}
