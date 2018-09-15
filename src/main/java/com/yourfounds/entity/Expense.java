package com.yourfounds.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;

    @Column(name = "sum")
    private double sum;

    @Column(name = "category_id")
    private int category;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "comment")
    private String comment;

    @Column(name = "user_id")
    private int userId;

    public Expense() {
    }

    public Expense(double sum, int category, LocalDate date, LocalTime time, String comment, int userId) {
        this.sum = sum;
        this.category = category;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.userId = userId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int categoryId) {
        this.category = categoryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
