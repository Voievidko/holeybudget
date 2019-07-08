package com.yourfounds.service;

import com.yourfounds.entity.Currency;

import java.util.List;

public interface CurrencyService {

    Currency get(String code);
    List<Currency> getAll();
    List<Currency> getAllCurrenciesAssignedToUser();
}
