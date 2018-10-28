package com.yourfounds.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "is required")
    @Min(value = 0, message = "Sum can't have minus value")
    @Max(value = 1000000, message = "Sum is too big")
    private double sum;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "comment")
    private String comment;

    @Column(name = "user_id")
    private int userId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "expense", cascade = CascadeType.ALL)
    private Category category;

    public Expense() {
    }

    public Expense(double sum, int categoryId, LocalDate date, LocalTime time, String comment, int userId) {
        this.sum = sum;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
