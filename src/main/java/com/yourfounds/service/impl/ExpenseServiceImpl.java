package com.yourfounds.service.impl;

import com.yourfounds.dao.ExpenseDao;
import com.yourfounds.entity.Expense;
import com.yourfounds.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDao expenseDAO;

    @Override
    @Transactional
    public void addExpense(Expense expense) {
        expenseDAO.save(expense);
    }

    @Override
    @Transactional
    public List<Expense> getAllExpenses() {
        List <Expense> expenses = expenseDAO.getAll();
        Collections.sort(expenses);
        return expenses;
    }

    @Override
    @Transactional
    public Expense getExpenseById(int id) {
        return expenseDAO.get(id);
    }

    @Override
    @Transactional
    public List<Expense> getExpensesDuringCurrentMonth() {
        List <Expense> expenses = expenseDAO.getAllBetweenDates(getFirstDayOfCurrentMonth(), getLastDayOfCurrentMonth());
        Collections.sort(expenses);
        return expenses;
    }

    @Override
    @Transactional
    public void updateExpense(Expense expense) {
        expenseDAO.update(expense);
    }

    @Override
    @Transactional
    public void deleteExpenseById(int id) {
        expenseDAO.delete(id);
    }

    @Override
    public String getCurrentMonthName() {
        return Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }

    private Date getFirstDayOfCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    private Date getLastDayOfCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}
