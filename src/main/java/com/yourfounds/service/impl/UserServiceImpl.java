package com.yourfounds.service.impl;

import com.yourfounds.dao.UserDao;
import com.yourfounds.entity.User;
import com.yourfounds.service.UserService;
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
    public List<User> getUsers() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public User getUser(String id) {
        return userDao.get(id);
    }

    @Override
    @Transactional
    public void deleteUserById(String id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }
}
