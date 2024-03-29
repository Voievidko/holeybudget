package com.holeybudget.service.impl;

import com.holeybudget.dao.UserDao;
import com.holeybudget.entity.User;
import com.holeybudget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public User getUser(String username) {
        return userDao.get(username);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        userDao.delete(username);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }
}
