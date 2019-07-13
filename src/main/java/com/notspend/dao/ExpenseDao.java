package com.notspend.dao;

import com.notspend.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseDao extends CrudDao<Expense,Integer> {
    Double getSumExpensesBetweenDates(LocalDate dateFrom, LocalDate dateTo);
    Double getSumIncomeBetweenDates(LocalDate dateFrom, LocalDate dateTo);
    List<Expense> getExpensesBetweenDates(LocalDate dateFrom, LocalDate dateTo);
    List<Expense> getIncomesBetweenDates(LocalDate dateFrom, LocalDate dateTo);
}
