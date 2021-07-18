package com.holeybudget.service;

import com.holeybudget.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getAllUsers();
    User getUser(String username);
    void deleteUserByUsername(String username);
    void updateUser(User user);
}
