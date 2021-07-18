package com.holeybudget.dao.impl;

import com.holeybudget.dao.CurrencyDao;
import com.holeybudget.entity.Account;
import com.holeybudget.entity.Currency;
import com.holeybudget.entity.User;
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
    public Currency get(Integer number) {
        Session session = sessionFactory.getCurrentSession();
        Query<Currency> query = session.createQuery("FROM Currency where number = :number", Currency.class);
        query.setParameter("number", number);
        return query.list().stream().findFirst().orElseThrow();
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
