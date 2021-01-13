package com.notspend.entity;

import com.notspend.validation.ValidEmail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = "username")
@ToString(of = {"username", "email", "name", "surname", "enabled", })
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
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    private List<Account> accounts = new ArrayList<>();

    //for bi-directional relationship
    public void addExpence(Expense expence) {
        expenses.add(expence);
        expence.setUser(this);
    }

    //for bi-directional relationship
    public void addAccount(Account account){
        accounts.add(account);
        account.setUser(this);
    }
}
