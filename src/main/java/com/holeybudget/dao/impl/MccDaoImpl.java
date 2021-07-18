package com.holeybudget.dao.impl;

import com.holeybudget.dao.MccDao;
import com.holeybudget.entity.Mcc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MccDaoImpl implements MccDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String getCategoryByMccCode(Integer code) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Mcc.class, code).getCategoryName();
    }
}
