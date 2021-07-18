package com.holeybudget.dao.impl;

import com.holeybudget.dao.ExpenseDao;
import com.holeybudget.entity.Expense;
import com.holeybudget.util.SecurityUserHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
       return session.createQuery("SELECT e FROM Expense e LEFT JOIN e.category where e.category.income = FALSE AND e.user.username = :param ORDER BY e.date DESC")
                .setParameter("param", SecurityUserHandler.getCurrentUser())
                .list();
    }

    @Override
    public Expense get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Expense.class, id);
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
    public List<Expense> getExpensesBetweenDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT e FROM Expense e LEFT JOIN e.category WHERE e.category.income = FALSE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param ORDER BY e.date DESC")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", SecurityUserHandler.getCurrentUser())
                .list();
    }

    @Override
    public List<Expense> getIncomesBetweenDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT e FROM Expense e LEFT JOIN e.category WHERE e.category.income = TRUE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param ORDER BY e.date DESC")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", SecurityUserHandler.getCurrentUser())
                .list();
    }

    @Override
    public Double getSumExpensesBetweenDates(LocalDate dateFrom, LocalDate dateTo){
        Session session = sessionFactory.getCurrentSession();
        Double result = (Double)session.createQuery("SELECT SUM(e.sum) FROM Expense e LEFT JOIN e.category " +
                "WHERE e.category.income = FALSE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", SecurityUserHandler.getCurrentUser()) // AND e.user = :param
                .getSingleResult();
        if (result == null) return 0d;
        return result;
    }

    @Override
    public Double getSumIncomeBetweenDates(LocalDate dateFrom, LocalDate dateTo){
        Session session = sessionFactory.getCurrentSession();
        Double result = (Double)session.createQuery("SELECT SUM(e.sum) FROM Expense e LEFT JOIN e.category " +
                "WHERE e.category.income = TRUE AND e.date BETWEEN :stDate AND :edDate AND e.user.username = :param")
                .setParameter("stDate", dateFrom)
                .setParameter("edDate", dateTo)
                .setParameter("param", SecurityUserHandler.getCurrentUser())
                .getSingleResult();
        if (result == null) return 0d;
        return result;
    }
}
