package com.yourfounds.service.impl;

import com.yourfounds.dao.CurrencyDao;
import com.yourfounds.entity.Account;
import com.yourfounds.entity.Currency;
import com.yourfounds.entity.User;
import com.yourfounds.service.CurrencyService;
import com.yourfounds.service.UserService;
import com.yourfounds.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Currency get(String code) {
        return currencyDao.get(code);
    }

    @Override
    @Transactional
    public List<Currency> getAll() {
        return currencyDao.getAll();
    }

    @Override
    @Transactional
    public List<Currency> getAllCurrenciesAssignedToUser() {
        String username = Util.getCurrentUser();
        User user = userService.getUser(username);
        return currencyDao.getAllCurrenciesAssignedToUser(user);
    }
}
