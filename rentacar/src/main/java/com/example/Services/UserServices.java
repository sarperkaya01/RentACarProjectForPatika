package com.example.Services;

import java.sql.Connection;
import java.util.List;

import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.User;

public class UserServices {
     private UserDao userDao;

    public UserServices(Connection conn) {
        this.userDao = new UserDao(conn);
    }

    public void register(String email, String password) {
        byte[] hash = HashUtil.sha256(password);
        User user = new User(null, email, hash);
        userDao.add(user);
    }
    public User getUserByEmail(String email) {
    return getAllUsers().stream()
            .filter(user -> user.getEmail().equalsIgnoreCase(email))
            .findFirst()    
            .orElse(null); 
}
    public void updateUser(User item){
        userDao.update(item);

    }

    public boolean login(String email, String password) {
        return userDao.login(email, password);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public void deleteUser(int id) {
        userDao.delete(id);
    }
}
