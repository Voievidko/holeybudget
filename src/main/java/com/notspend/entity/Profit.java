package com.notspend.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Profit{

    private int year;
    private String month;
    private double monthIncome;
    private double monthExpense;
    private double profit;

    public Profit(int year, String month, double monthIncome, double monthExpense) {
        this.year = year;
        this.month = month;
        this.monthIncome = monthIncome;
        this.monthExpense = monthExpense;
    }

}
