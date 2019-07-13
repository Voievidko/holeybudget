package com.notspend.dao.impl;

import com.notspend.dao.CurrencyDao;
import com.notspend.entity.Account;
import com.notspend.entity.Currency;
import com.notspend.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Currency get(String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Currency.class, id);
    }

    @Override
    public List<Currency> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Currency> query = session.createQuery("FROM Currency", Currency.class);
        return query.getResultList();
    }

    @Override
    public void save(Currency currency) {
        Session session = sessionFactory.getCurrentSession();
        session.save(currency);
    }

    @Override
    public void update(Currency currency) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(currency);
    }

    @Override
    public void delete(String id) {
        Session session = sessionFactory.getCurrentSession();
        Currency currency = session.get(Currency.class, id);
        session.delete(currency);
    }

    @Override
    public List<Currency> getAllCurrenciesAssignedToUser(User user) {
        return user.getAccounts().stream().map(Account::getCurrency).distinct().collect(Collectors.toList());
    }
}
