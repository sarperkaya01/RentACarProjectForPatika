package com.example.Entities.DbModels.People;

import java.util.Arrays;

import com.example.Services.Enums.UserRoles;

public class User {
    private Integer userId;
    private String email;
    private byte[] passwd;
    private UserRoles role;
    
   
    public User(Integer userId, String email, byte[] passwd, UserRoles role) {
        this.userId = userId;
        this.email = email;
        this.passwd = passwd;
        this.role = role;
    }
    
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", email=" + email + ", passwd=" + Arrays.toString(passwd) + ", role=" + role
                + "]";
    }


    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getPasswd() {
        return passwd;
    }
    public void setPasswd(byte[] passwd) {
        this.passwd = passwd;
    }
     public UserRoles getRole() {
        return role;
    }
    public void setRole(UserRoles role) {
        this.role = role;
    }
    
}
