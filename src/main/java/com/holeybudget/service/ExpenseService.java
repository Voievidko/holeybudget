package com.holeybudget.service;

import com.holeybudget.entity.Expense;

import java.util.List;

public interface ExpenseService {
    void addExpense(Expense expense);
    Expense getExpenseById(int id);
    void updateExpense(Expense expense);
    List<Expense> getAllExpenses();
    List<Expense> getExpensesDuringCurrentMonth();
    List<Expense> getIncomesDuringCurrentMonth();
    void deleteExpenseById(int id);

    String getCurrentMonthName();
    Double getSumOfExpenseForCurrentMonth();
    Double getSumOfIncomeForCurrentMonth();
    List<Expense> getAllIncomeDuringYear();
    List<Expense> getAllExpenseDuringYear();
}
