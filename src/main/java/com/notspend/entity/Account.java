package com.notspend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "type")
    @NotNull(message = "Name of your account is required")
    @NotEmpty(message = "Name of your account is required")
    private String type;

    @Column(name = "summary")
    @NotNull(message = "Started sum of your account is required. Can be 0 or minus")
    private Double summary;

    @Column(name = "description")
    private String description;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="currency_code")
    private Currency currency;

    @Column(name = "synchronization_token")
    private String token;

    @Column(name = "synchronization_id")
    private String synchronizationId;

    @Column(name = "synchronization_time")
    private Long synchronizationTime;

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

    public Double getSummary() {
        return summary;
    }

    public void setSummary(Double summary) {
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

    public Currency getCurrency() {
        return currency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSynchronizationId() {
        return synchronizationId;
    }

    public void setSynchronizationId(String synchronizationId) {
        this.synchronizationId = synchronizationId;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getSynchronizationTime() {
        return synchronizationTime;
    }

    public void setSynchronizationTime(Long synchronizationTime) {
        this.synchronizationTime = synchronizationTime;
    }

    public void substract(Double sum){
        this.summary = this.summary - sum;
    }

    public void plus(Double sum){
        this.summary = this.summary + sum;
    }

    public void minus(Double sum){
        this.summary = this.summary - sum;
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
