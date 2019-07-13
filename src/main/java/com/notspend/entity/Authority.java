package com.notspend.entity;

import javax.persistence.*;

@Entity
@Table(name = "authority")
public class Authority {

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @Id
    @Column(name = "authority", nullable = false)
    private String authority;

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
