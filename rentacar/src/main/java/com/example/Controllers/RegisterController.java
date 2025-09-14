package com.example.Controllers;

import java.sql.Connection;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import com.example.Services.DbTransactions.RegistrationTransactions;

import com.example.Services.Interfaces.Controller;

public class RegisterController implements Controller {
    private Connection conn;
    private Scanner sc;

    public RegisterController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    @Override
    public void start() {
        pageTitle("Register");
        printMenu();

        List<Runnable> actions = Arrays.asList(
                this::addUser,
                this::exit);

        int choice = sc.nextInt();
        sc.nextLine();
        menuCases(choice, actions);
    }

    @Override
    public void exit() {
        MainController mc = new MainController(conn, sc);
        mc.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("New User", "Exit");
    }

   

    private void addUser() {
        RegistrationTransactions rt = new RegistrationTransactions();
        rt.registerNewUser();

        exit();

    }

}
