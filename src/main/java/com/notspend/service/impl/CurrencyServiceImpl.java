package com.notspend.service.impl;

import com.notspend.dao.CurrencyDao;
import com.notspend.entity.Currency;
import com.notspend.entity.User;
import com.notspend.service.CurrencyService;
import com.notspend.service.UserService;
import com.notspend.util.SecurityUserHandler;
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
