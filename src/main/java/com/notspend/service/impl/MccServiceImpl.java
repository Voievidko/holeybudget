package com.notspend.service.impl;

import com.notspend.dao.MccDao;
import com.notspend.service.MccService;
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
