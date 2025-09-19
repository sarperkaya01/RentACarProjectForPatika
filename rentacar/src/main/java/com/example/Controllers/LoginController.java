package com.example.Controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.People.User;
import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.Interfaces.Controller;

@Component
public class LoginController implements Controller {

    private final UserServices userServices;
    private final MainMenuController mainMenuController;

    @Autowired
    public LoginController(UserServices userServices, MainMenuController mainMenuController) {
        this.userServices = userServices;
        this.mainMenuController = mainMenuController;
    }

    @Override
    public void start() {
        runMenuLoop("Log In");
        System.out.println("Email:");
        String email = Global.scanner.nextLine();
        System.out.println("Password:");
        String passwd = Global.scanner.nextLine();

        User u;

        do {
            u = login(email, passwd);

            if (u == null) {
                System.out.println("Email or password is wrong ! Please try again...");
            }

        } while (u == null);
        Global.currentUser = u;

        mainMenuController.start();

    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        return Collections.emptyList();
    }

    public User login(String email, String password) {

        User checkUser = userServices.getUserByEmail(email);
        if (checkUser == null) {
            return null;
        }

        byte[] storedHash = checkUser.getPasswd();
        byte[] inputHash = HashUtil.sha256(password);

        return Arrays.equals(storedHash, inputHash) ? checkUser : null;

    }

}
