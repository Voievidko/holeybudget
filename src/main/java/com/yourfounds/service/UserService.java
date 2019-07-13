package com.yourfounds.service;

import com.yourfounds.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getAllUsers();
    User getUser(String id);
    void deleteUserByUsername(String username);
    void updateUser(User user);
}
