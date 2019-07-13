package com.notspend.service.impl;

import com.notspend.dao.UserDao;
import com.notspend.entity.User;
import com.notspend.service.UserService;
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
    public User getUser(String id) {
        return userDao.get(id);
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
