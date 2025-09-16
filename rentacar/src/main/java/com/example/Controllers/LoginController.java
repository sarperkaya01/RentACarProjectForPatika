package com.example.Controllers;

import java.util.Arrays;
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
    

    @Autowired
    public LoginController(UserServices userServices) {

        this.userServices = userServices;

    }

    @Override
    public void start() {
        pageTitle("Log In");
        System.out.println("Email:");
        String email = Global.scanner.nextLine();
        System.out.println("Password:");
        String passwd = Global.scanner.nextLine();

        do {

            if (login(email, passwd) == null) {
                System.out.println("Email or password is wrong ! Please try again...");
            }

        } while (login(email, passwd) == null);
        MainMenuController mmc = new MainMenuController();
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

        User checkUser = userServices.getUserByEmail(email);
        if (checkUser == null) {
            return null;
        }

        byte[] storedHash = checkUser.getPasswd(); // hash from db
        byte[] inputHash = HashUtil.sha256(password); // user input hash

        return Arrays.equals(storedHash, inputHash) ? checkUser : null;

    }

}
