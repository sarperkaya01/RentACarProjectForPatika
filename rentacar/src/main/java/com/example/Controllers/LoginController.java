package com.example.Controllers;

import java.sql.Connection;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.User;

import com.example.Services.HashUtil;
import com.example.Services.Interfaces.Controller;

public class LoginController implements Controller {
    private Connection conn;
    private Scanner sc;

    public LoginController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    @Override
    public void start() {
        pageTitle("Log In");
        System.out.println("Email:");
        String email = sc.nextLine();
        System.out.println("Password:");
        String passwd = sc.nextLine();

        do {

            if (login(email, passwd) == null) {
                System.out.println("Email or password is wrong ! Please try again...");
            }

        } while (login(email, passwd) == null);
        MainMenuController mmc = new MainMenuController(conn, sc);
        mmc.start();

    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("");
    }

    public User login(String email, String password) {

        UserDao ud = new UserDao(conn);
        User checkUser = ud.getyByEmail(email);

        byte[] storedHash = checkUser.getPasswd(); // hash from db
        byte[] inputHash = HashUtil.sha256(password); // user input hash

        return Arrays.equals(storedHash, inputHash) ? checkUser : null;

    }

}
