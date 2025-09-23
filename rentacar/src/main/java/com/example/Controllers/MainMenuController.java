package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;

@Component
public class MainMenuController implements Controller {
    private final VehicleOperationsController vehicleMenuController;
    private final RentalsController rentalsController;
    private final ProfileController profileController;

    public MainMenuController(VehicleOperationsController vehicleMenuController, RentalsController rentalsController,
            ProfileController profileController) {
        this.vehicleMenuController = vehicleMenuController;
        this.rentalsController = rentalsController;
        this.profileController = profileController;
    }

    @Override
    public void start() {
        runMenuLoop("Main Menu");
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public List<String> getMenuTitles() {
        List<String> menuCases = new ArrayList<>(Arrays.asList("Vehicles", "Rentals"));

        if (Global.currentUser != null) {
            if (Global.currentUser.getRole() == UserRoles.ADMIN) {
                menuCases.add("Settings");
            } else {
                menuCases.add("Profile");
            }
        }

        menuCases.add("Exit");

        return menuCases;
    }

    @SuppressWarnings("unused")
    private void vehicles() {
        vehicleMenuController.start();

    }

    @SuppressWarnings("unused")
    private void rentals() {
        rentalsController.start();
    }

    @SuppressWarnings("unused")
    private void settings() {

    }

    @SuppressWarnings("unused")
    private void profile() {
        profileController.start();

    }

}
