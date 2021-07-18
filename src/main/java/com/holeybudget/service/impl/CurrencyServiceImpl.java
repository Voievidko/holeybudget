package com.holeybudget.service.impl;

import com.holeybudget.dao.CurrencyDao;
import com.holeybudget.entity.Currency;
import com.holeybudget.entity.User;
import com.holeybudget.service.CurrencyService;
import com.holeybudget.service.UserService;
import com.holeybudget.util.SecurityUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Currency getCurrencyByCode(String code) {
        return currencyDao.get(code);
    }

    @Override
    @Transactional
    public Currency getCurrencyByNumber(Integer number) {
        return currencyDao.get(number);
    }

    @Override
    @Transactional
    public List<Currency> getAllCurrencies() {
        return currencyDao.getAll();
    }

    @Override
    @Transactional
    public List<Currency> getAllCurrenciesAssignedToUser() {
        String username = SecurityUserHandler.getCurrentUser();
        User user = userService.getUser(username);
        return currencyDao.getAllCurrenciesAssignedToUser(user);
    }
}
