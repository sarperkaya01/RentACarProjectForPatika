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
    private final VehiclesController vehiclesController;
    private final RentalsController rentalsController;

    public MainMenuController(VehiclesController vehiclesController, RentalsController rentalsController) {
        this.vehiclesController = vehiclesController;
        this.rentalsController = rentalsController;
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

    private void vehicles() {
        vehiclesController.start();

    }

    private void rentals() {
        rentalsController.start();
    }

    private void settings() {

    }

    private void profile() {

    }

}
