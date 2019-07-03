package com.yourfounds.dao.impl;

import com.yourfounds.dao.ExpenseDao;
import com.yourfounds.entity.Category;
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
        List<Expense> expenses = session.createQuery("SELECT e FROM Expense e LEFT JOIN e.category where e.category.income = FALSE AND e.user.username = :param")
                .setParameter("param", Util.getCurrentUser())
                .list();
        return expenses;
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
    public List<Expense> getAllExpenseBetweenDates(Date dateFrom, Date dateTo) {
        Session session = sessionFactory.getCurrentSession();
        List<Expense> expenses = session.createQuery("SELECT e FROM Expense e LEFT JOIN e.category where e.category.income = FALSE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", Util.getCurrentUser())
                .list();
        return expenses;
    }

    @Override
    public Double getSumExpensesBetweenDates(Date dateFrom, Date dateTo){
        Session session = sessionFactory.getCurrentSession();
        // select c.customerName, c.customerCity, i.itemName,i.price from Customer c left join c.items i
        Double result = (Double)session.createQuery("SELECT SUM(e.sum) FROM Expense e LEFT JOIN e.category " +
                "WHERE e.category.income = FALSE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", Util.getCurrentUser()) // AND e.user = :param
                .getSingleResult();
        if (result == null) return 0d;
        return result;
    }

    @Override
    public Double getSumIncomeBetweenDates(Date dateFrom, Date dateTo){
        Session session = sessionFactory.getCurrentSession();
        Double result = (Double)session.createQuery("SELECT SUM(e.sum) FROM Expense e LEFT JOIN e.category " +
                "WHERE e.category.income = TRUE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", Util.getCurrentUser())
                .getSingleResult();
        if (result == null) return 0d;
        return result;
    }
}
