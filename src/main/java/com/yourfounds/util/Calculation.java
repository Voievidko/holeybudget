package com.yourfounds.util;

import com.yourfounds.entity.Expense;

import java.util.List;

public class Calculation {
    public static Double exspenseSum (List<Expense> expenses){
        return expenses.stream().map(a -> a.getSum()).reduce(0d, (a, b) -> a + b);
    }
}
