package com.example.Controllers;

import com.example.Entities.DbModels.People.User;
import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class LoginController {

    private final UserServices userServices;
    private final MainMenuController mainMenuController;

    @Autowired
    public LoginController(UserServices userServices, MainMenuController mainMenuController) {
        this.userServices = userServices;
        this.mainMenuController = mainMenuController;
    }

    public void start() {
        System.out.println("\n--- Welcome to the Rent-A-Car System ---");
        System.out.println("Please log in to continue.");

        while (true) {
            System.out.print("Email: ");
            String email = Global.scanner.nextLine();
            System.out.print("Password: ");
            String password = Global.scanner.nextLine();

            Optional<User> loggedInUser = attemptLogin(email, password);

            if (loggedInUser.isPresent()) {

                Global.currentUser = loggedInUser.get();
                System.out.println("\nLogin successful! Welcome, " + Global.currentUser.getEmail());
                mainMenuController.start();
                break;
            } else {

                System.out.println("Email or password is wrong! Please try again...");
            }
        }
    }

    private Optional<User> attemptLogin(String email, String password) {

        Optional<User> userOptional = userServices.getUserByEmail(email);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User foundUser = userOptional.get();
        byte[] storedHash = foundUser.getPasswd();
        byte[] inputHash = HashUtil.sha256(password);

        if (Arrays.equals(storedHash, inputHash)) {
            return Optional.of(foundUser);
        } else {
            return Optional.empty();
        }
    }
}