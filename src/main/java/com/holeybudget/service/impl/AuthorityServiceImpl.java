package com.holeybudget.service.impl;

import com.holeybudget.dao.AuthorityDao;
import com.holeybudget.entity.Authority;
import com.holeybudget.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    @Transactional
    public void saveAuthority(Authority authority) {
        authorityDao.save(authority);
    }
}
