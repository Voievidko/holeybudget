package com.holeybudget.service;

import com.holeybudget.entity.Expense;
import com.holeybudget.entity.Profit;

import java.util.List;

public interface ProfitService {
    List<Profit> getProfitTableDuringLastYear(List<Expense> currentYearIncome, List<Expense> currentYearExpense, String outcomeCode);
}
