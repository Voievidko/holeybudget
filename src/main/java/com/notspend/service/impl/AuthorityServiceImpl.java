package com.notspend.service.impl;

import com.notspend.dao.AuthorityDao;
import com.notspend.entity.Authority;
import com.notspend.service.AuthorityService;
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
