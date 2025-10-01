package com.example.Controllers;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.People.User;
import com.example.Services.UserServices;
import com.example.Utils.Global;

import com.example.Utils.Interfaces.Controller;

@Component
public class MainController implements Controller {
    private final RegisterController registerController;
    private final LoginController loginController;
    private final UserServices userServices;

    @Autowired
    public MainController(RegisterController registerController, LoginController loginController,
            UserServices userServices) {
        this.registerController = registerController;
        this.loginController = loginController;
        this.userServices = userServices;
    }

    @Override
    public void start() {

        runMenuLoop("Welcome my rent a car app");
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Register", "Login", "Forgot Password", "Exit");
    }

    @SuppressWarnings("unused")
    private void register() {
        registerController.start();

    }

    @SuppressWarnings("unused")
    private void login() {
        loginController.start();
    }

    @SuppressWarnings("unused")
    private void forgotPassword() {
        System.out.println("Email :");
        String email = Global.scanner.nextLine();

        userServices.getUserByEmail(email).ifPresentOrElse(user -> {
            boolean loop = true;
            while (loop) {
                System.out.print("Enter Password: ");
                String password = Global.scanner.nextLine();
                System.out.print("Confirm Password: ");
                String passwordConfirm = Global.scanner.nextLine();
                if (password.equals(passwordConfirm)) {
                    User u2 = userServices.updatePasswd(user.getUserId(), passwordConfirm);
                    if (u2 != null) {
                        System.out.println("Password changed !");
                        loop = false;
                    }

                }
            }

        },
                () -> {

                    System.out.println("There is no user about that email :" + email);
                });

    }

    @Override
    public void exit() {
        System.out.println("Exiting the application. Goodbye!");
        System.exit(0);
    }
}
