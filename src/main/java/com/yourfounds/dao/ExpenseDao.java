package com.yourfounds.dao;

import com.yourfounds.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseDao extends CrudDao<Expense,Integer> {
    Double getSumExpensesBetweenDates(LocalDate dateFrom, LocalDate dateTo);
    Double getSumIncomeBetweenDates(LocalDate dateFrom, LocalDate dateTo);
    List<Expense> getAllExpenseBetweenDates(LocalDate dateFrom, LocalDate dateTo);
    List<Expense> getAllIncomeBetweenDates(LocalDate dateFrom, LocalDate dateTo);
}
