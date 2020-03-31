package com.notspend.service;

import com.notspend.entity.Currency;

import java.util.List;

public interface CurrencyService {

    Currency getCurrencyByCode(String code);
    Currency getCurrencyByNumber(Integer number);
    List<Currency> getAllCurrencies();
    List<Currency> getAllCurrenciesAssignedToUser();
}
