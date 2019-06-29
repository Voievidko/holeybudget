package com.yourfounds.dao;

import com.yourfounds.entity.Expense;

import java.util.Date;
import java.util.List;

public interface ExpenseDao extends CrudDao<Expense,Integer> {
    List<Expense> getAllBetweenDates(Date dateFrom, Date dateTo);
}
