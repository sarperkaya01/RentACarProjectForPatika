package com.example.Controllers;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import com.example.Services.Interfaces.Controller;

public class MainController implements Controller {
    private Connection conn;
    private Scanner sc;

    public MainController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    @Override
    public void start() {
        pageTitle("Main Menu");
        printMenu();

        List<Runnable> actions = Arrays.asList(
                this::registerPage,
                this::loginPage,
                // this::forgotPassword,
                this::exit);

        int choice = sc.nextInt();
        sc.nextLine();
        boolean checkInput = menuCases(choice, actions);
        if (!checkInput) {
            start();
        }
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Register", "Login", "Forget Passoword", "Exit");
    }

    private void registerPage() {
        RegisterController rc = new RegisterController(conn,sc);
        rc.start();

    }

    private void loginPage() {
        LoginController lc = new LoginController(conn, sc);
        lc.start();
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
