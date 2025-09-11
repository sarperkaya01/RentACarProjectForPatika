package com.example.Entities.DbModels.People;

public class User {
    private Integer userId;
    private String email;
    private String passwd;
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
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
}
