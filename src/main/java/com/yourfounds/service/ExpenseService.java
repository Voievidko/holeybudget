package com.yourfounds.service;

import com.yourfounds.entity.Expense;

import java.util.List;

public interface ExpenseService {
    void addExpense(Expense expense);
    Expense getExpenseById(int id);
    void updateExpense(Expense expense);
    List<Expense> getAllExpenses();
    List<Expense> getExpensesDuringCurrentMonth();
    void deleteExpenseById(int id);

    String getCurrentMonthName();
    Double getSumOfExpenseForCurrentMonth();
    Double getSumOfIncomeBetweenDates();
    List<Expense> getAllIncomeDuringYear();
}
