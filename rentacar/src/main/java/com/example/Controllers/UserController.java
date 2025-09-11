package com.example.Controllers;



import java.util.Arrays;

import java.util.List;

import java.util.Scanner;

import com.example.Services.ControllerInterface;



public class UserController implements ControllerInterface {

    // private UserServices userService;
    private Scanner scanner;
    // private static UserServices us;

    // static {
    //     try {
    //         us = new UserServices(Global.getConnection());
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw new RuntimeException("Connection failed...", e);
    //     }
    // }

    public UserController(Scanner sc) {
        this.scanner = sc;

    }

    @Override
    public void start() {
        pageTitle("Login Page");
        printMenu();

        List<Runnable> actions = Arrays.asList(
                this::registerUser,
                this::loginUser,
                // this::forgotPassword,
                // this::profile,
                this::exitPage);

        int choice = scanner.nextInt();
        scanner.nextLine();
        menuCases(choice, actions);

    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Register", "Login", "Exit");
    }

    

    private void registerUser() {
        pageTitle("Register Page");
    }

    private void loginUser() {
        // login menu
    }

    // private void forgotPassword() {
    // System.out.println("Forgot Password seçildi");
    // }

    // private void profile() {
    // System.out.println("Profile seçildi");
    // }

    private void exitPage() {
        System.exit(0);
    }

  

}
