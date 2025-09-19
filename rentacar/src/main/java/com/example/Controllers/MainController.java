package com.example.Controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Utils.Global;
import com.example.Utils.Interfaces.Controller;

@Component
public class MainController implements Controller {
    private final RegisterController registerController;
    private final LoginController loginController;

    @Autowired
    public MainController(RegisterController registerController, LoginController loginController) {
        this.registerController = registerController;
        this.loginController = loginController;
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
    private void forgotpassword() {
        System.out.println("Forgot password functionality is not implemented yet.");
    }

    @Override
    public void exit() {
        System.out.println("Exiting the application. Goodbye!");
    }
}
