package com.holeybudget.dao.impl;

import com.holeybudget.dao.MccDao;
import com.holeybudget.entity.Mcc;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@CommonsLog
public class MccDaoImpl implements MccDao {

    private static final Integer UNKNOWN_MCC_CODE = 9999;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String getCategoryByMccCode(Integer code) {
        Session session = sessionFactory.getCurrentSession();
        Mcc mcc = session.get(Mcc.class, code);
        if (mcc == null) {
            log.warn("Table with MCC codes doesn't have code: '" + code +
                "'. Use unknown code '" + UNKNOWN_MCC_CODE + "' instead.");
            mcc = session.get(Mcc.class, UNKNOWN_MCC_CODE);
        }
        return mcc.getCategoryName();
    }
}
