package com.notspend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(of = {"accountId"})
@ToString(of = {"accountId", "type", "summary", "user", "summary"})
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

    public void substract(Double sum){
        this.summary = this.summary - sum;
    }

    public void plus(Double sum){
        this.summary = this.summary + sum;
    }

    public void minus(Double sum){
        this.summary = this.summary - sum;
    }

}
