package com.yourfounds.dao.impl;

import com.yourfounds.dao.ExpenseDao;
import com.yourfounds.entity.Expense;
import com.yourfounds.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Expense expense) {
        Session session = sessionFactory.getCurrentSession();
        session.save(expense);
    }

    @Override
    public List<Expense> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Expense> query = session.createQuery("FROM Expense WHERE username = :param", Expense.class);
        query.setParameter("param", Util.getCurrentUser());
        return query.getResultList();
    }

    @Override
    public Expense get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Expense expense = session.get(Expense.class, id);
        return expense;
    }

    @Override
    public void update(Expense expense) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(expense);
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Expense expense = session.get(Expense.class, id);
        session.delete(expense);
    }

    @Override
    public List<Expense> getAllBetweenDates(Date dateFrom, Date dateTo) {
        Session session = sessionFactory.getCurrentSession();
        List<Expense> expenses = session.createQuery("FROM Expense AS e WHERE e.date BETWEEN :stDate AND :edDate AND username = :param")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", Util.getCurrentUser())
                .list();
        return expenses;
    }
}
