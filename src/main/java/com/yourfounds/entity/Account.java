package com.yourfounds.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "type")
    private String type;

    @Column(name = "summary")
    private double summary;

    @Column(name = "description")
    private String description;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "username")
    private User user;

    public Account() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void substract(double sum){
        this.summary = this.summary - sum;
    }

    public void plus(double sum){
        this.summary = this.summary + sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getAccountId() == account.getAccountId() &&
                Double.compare(account.getSummary(), getSummary()) == 0 &&
                Objects.equals(getType(), account.getType()) &&
                Objects.equals(getDescription(), account.getDescription()) &&
                Objects.equals(getUser().getUsername(), account.getUser().getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getType(), getSummary(), getDescription(), getUser());
    }
}
