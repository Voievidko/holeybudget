package com.yourfounds.service;

import com.yourfounds.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getUsers();
    User getUser(String id);
    void deleteUserById(String id);
    void updateUser(User user);
}
