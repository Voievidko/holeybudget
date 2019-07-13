package com.yourfounds.service;

import com.yourfounds.entity.Currency;

import java.util.List;

public interface CurrencyService {

    Currency getCurrencyByCode(String code);
    List<Currency> getAllCurrencies();
    List<Currency> getAllCurrenciesAssignedToUser();
}
