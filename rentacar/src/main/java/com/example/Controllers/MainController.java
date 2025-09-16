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
        pageTitle("Main Menu");
        printMenu();

        List<Runnable> actions = Arrays.asList(
                this::registerPage,
                this::loginPage,
                // this::forgotPassword,
                this::exit);

        int choice =Global.scanner.nextInt();
        Global.scanner.nextLine();
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
        registerController.start();

    }

    private void loginPage() {
        loginController.start();
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
