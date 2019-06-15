package com.yourfounds.dao.impl;

import com.yourfounds.dao.AccountDao;
import com.yourfounds.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Account get(int id) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.get(Account.class, id);
        return account;
    }

    @Override
    public List<Account> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Account> query = session.createQuery("FROM Account", Account.class);
        return query.getResultList();
    }

    @Override
    public void save(Account account) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(account);
    }

    @Override
    public void update(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(account);
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.get(Account.class, id);
        session.delete(account);
    }
}
