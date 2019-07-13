package com.notspend.service;

import com.notspend.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getAllUsers();
    User getUser(String id);
    void deleteUserByUsername(String username);
    void updateUser(User user);
}
