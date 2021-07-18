package com.holeybudget.dao;

import com.holeybudget.entity.Currency;
import com.holeybudget.entity.User;

import java.util.List;

public interface CurrencyDao extends CrudDao<Currency, String> {

    List<Currency> getAllCurrenciesAssignedToUser(User user);
    Currency get(Integer number);

}
