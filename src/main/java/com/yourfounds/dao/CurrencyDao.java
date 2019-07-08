package com.yourfounds.dao;

import com.yourfounds.entity.Currency;
import com.yourfounds.entity.User;

import java.util.List;

public interface CurrencyDao extends CrudDao<Currency, String> {

    List<Currency> getAllCurrenciesAssignedToUser(User user);

}
