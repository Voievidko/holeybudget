package com.holeybudget.service.impl;

import com.holeybudget.dao.MccDao;
import com.holeybudget.service.MccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MccServiceImpl implements MccService {

    @Autowired
    private MccDao mccDao;

    @Override
    @Transactional
    public String getCategoryByMccCode(Integer code) {
        return mccDao.getCategoryByMccCode(code);
    }
}
