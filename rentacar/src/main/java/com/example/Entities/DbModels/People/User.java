package com.example.Entities.DbModels.People;

import java.util.Arrays;

import com.example.Utils.Enums.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Bu sınıfın bir veritabanı varlığı olduğunu belirtir (en önemlisi)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    public User() {

    }

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "passwd", nullable = false)
    private byte[] passwd;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRoles role;

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
