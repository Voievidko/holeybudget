package com.notspend.entity;

import com.notspend.validation.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @NotEmpty(message = "Username is required")
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    @NotEmpty(message = "Email is required")
    @ValidEmail
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password is required")
    private String password;

    @Column(name = "name")
    @Pattern(regexp = "[a-zA-Z]*", message = "Only letters")
    private String name;

    @Column(name = "surname")
    @Pattern(regexp = "[a-zA-Z]*", message = "Only letters")
    private String surname;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany (mappedBy = "user",
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    private List<Expense> expenses;

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    private List<Account> accounts;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    //for bi-directional relationship
    public void setExpence(Expense expence) {
        if (expenses == null){
            expenses = new ArrayList<>();
        }
        expenses.add(expence);
        expence.setUser(this);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    //for bi-directional relationship
    public void setAccount(Account account){
        if(accounts == null){
            accounts = new ArrayList<>();
        }
        accounts.add(account);
        account.setUser(this);
    }
}