package com.example.Services;

import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.User;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServices {

    private final UserDao userDao;

    @Autowired
    public UserServices(UserDao userDao) {
        this.userDao = userDao;
    }

    // --- CRUD: CREATE ---

   
    
    
   
    @Transactional
    public User saveNewUser(User user) {
        userDao.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("Email " + user.getEmail() + " is already in use.");
                });
        
        return userDao.save(user);
    }


    // --- CRUD: READ ---

    public User getUserById(Integer id) {
        return userDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found with ID: " + id));
    }

    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    // --- CRUD: UPDATE ---

    @Transactional
    public User updateEmail(Integer userId, String newEmail) {
        User user = getUserById(userId);
        if (!user.getEmail().equalsIgnoreCase(newEmail) && userDao.findByEmail(newEmail).isPresent()) {
            throw new IllegalStateException("Email " + newEmail + " is already in use.");
        }
        user.setEmail(newEmail);
        return userDao.save(user);
    }

    @Transactional
    public User updatePasswd(Integer userId, String newPassword) {
        User user = getUserById(userId);
        user.setPasswd(HashUtil.sha256(newPassword)); 
        return userDao.save(user);
    }

    @Transactional
    public User updateRole(Integer userId, UserRoles newRole) {
        User user = getUserById(userId);
        user.setRole(newRole);
        return userDao.save(user);
    }
}