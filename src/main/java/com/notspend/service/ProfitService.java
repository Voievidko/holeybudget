package com.notspend.service;

import com.notspend.entity.Expense;
import com.notspend.entity.Profit;

import java.util.List;
import java.util.Map;

public interface ProfitService {
    List<Profit> getProfitTableDuringLastYear(List<Expense> currentYearIncome, List<Expense> currentYearExpense);
}
