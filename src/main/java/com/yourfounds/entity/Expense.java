package com.yourfounds.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "expense")
public class Expense implements Comparable<Expense>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;

    @Column(name = "sum")
    @NotNull(message = "is required")
    @Min(value = 0, message = "Sum can't have minus value")
    private double sum;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Date time;

    @Column(name = "comment")
    private String comment;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                         CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id") //user_id is a field in expense table
    private User user;

    public Expense() {
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Expense o) {
        //todo: review this design
        if (this.date.compareTo(o.date) == 0) {
            return this.time.compareTo(o.time);
        }
        return this.date.compareTo(o.date);
    }
}
