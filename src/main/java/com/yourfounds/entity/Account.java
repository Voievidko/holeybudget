package com.yourfounds.entity;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountID;

    @Column(name = "type")
    private String type;

    @Column(name = "summary")
    private double summary;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private int userId;

    public Account() {
    }

    public Account(String type, double summary, String description, int userId) {
        this.type = type;
        this.summary = summary;
        this.description = description;
        this.userId = userId;
    }

    public int getAccountID() {
        return accountID;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
