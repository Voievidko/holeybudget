package com.notspend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@EqualsAndHashCode(of = "expenseId")
@Table(name = "expense")
public class Expense implements Comparable<Expense>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Integer expenseId;

    @Column(name = "sum")
    @NotNull(message = "Sum of your expense is required")
    private Double sum;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                         CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="username") //username is a field in expense table
    private User user;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="currency_code")
    private Currency currency;

    @Override
    public int compareTo(Expense o) {
        //todo: review this design
        if (this.date.compareTo(o.date) == 0) {
            return this.time.compareTo(o.time);
        }
        return this.date.compareTo(o.date);
    }

}
