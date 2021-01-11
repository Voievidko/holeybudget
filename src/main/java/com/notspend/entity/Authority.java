package com.notspend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = {"authority"})
@Table(name = "authority")
public class Authority {

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @Id
    @Column(name = "authority", nullable = false)
    private String authority;

}
