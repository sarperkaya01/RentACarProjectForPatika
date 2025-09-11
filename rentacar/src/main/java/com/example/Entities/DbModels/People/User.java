package com.example.Entities.DbModels.People;

public class User {
    private Integer userId;
    private String email;
    private byte[] passwd;
    
    public User() {

    }
    public User(Integer userId, String email, byte[] passwd) {
        this.userId = userId;
        this.email = email;
        this.passwd = passwd;
    }
    @Override
    public String toString() {
        return "User [userId=" + userId + ", email=" + email + ", passwd=" + passwd + "]";
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
    
}
