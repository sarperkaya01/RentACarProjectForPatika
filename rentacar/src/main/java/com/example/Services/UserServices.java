package com.example.Services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.UserDao;

import com.example.Entities.DbModels.People.User;

public class UserServices {

    private final UserDao userDao;

    @Autowired
    public UserServices(UserDao ud) {
        this.userDao = ud;
    }

    @Transactional
    public User newUser(User newUser) {
        if (userDao.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalStateException("Email taken already");
        }
        return userDao.save(newUser);
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found : " + email));
    }

    @Transactional
    public User updateUser(User userToUpdate) {

        if (userToUpdate.getUserId() == null) {
            throw new IllegalArgumentException("Id can not be null.");
        }

        userDao.findById(userToUpdate.getUserId())
                .orElseThrow(() -> new IllegalStateException("User not found: " + userToUpdate.getUserId()));

        return userDao.save(userToUpdate);
    }

    public List<User> getAllUsers() {

        return userDao.findAll();
    }

    @Transactional
    public void deleteUser(int id) {

        userDao.deleteById(id);
    }

}
