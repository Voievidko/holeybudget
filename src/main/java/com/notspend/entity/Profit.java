package com.notspend.entity;

public class Profit{

    private int year;
    private String month;
    private double monthIncome;
    private double monthExpense;
    private double profit;

    public Profit() {
    }

    public Profit(int year, String month, double monthIncome, double monthExpense) {
        this.year = year;
        this.month = month;
        this.monthIncome = monthIncome;
        this.monthExpense = monthExpense;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(double monthIncome) {
        this.monthIncome = monthIncome;
    }

    public double getMonthExpense() {
        return monthExpense;
    }

    public void setMonthExpense(double monthExpense) {
        this.monthExpense = monthExpense;
    }

    public double getProfit() {
        return this.monthIncome - this.monthExpense;
    }
}
