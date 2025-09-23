package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.Services.VehicleService;
import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;

public class VehicleOperationsController implements Controller {
    private final MainMenuController mmc;
    private final VehicleService vs;

    public VehicleOperationsController(MainMenuController mmc, VehicleService vs) {
        this.mmc = mmc;
        this.vs = vs;
    }

    @Override
    public void start() {
        runMenuLoop("Vehicle Operations");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        List<String> menuCases = new ArrayList<>(Arrays.asList("Search Automobile", "Search Motorcycle", "Search Helicopter"));

        if (Global.currentUser != null) {
            if (Global.currentUser.getRole() == UserRoles.ADMIN) {
                menuCases.add("Insert Vehicle");
                menuCases.add("Update Vehicle");
                menuCases.add("Delete Vehicle");
            }
        }

        menuCases.add("Exit");

        return menuCases;
    }

    @SuppressWarnings("unused")
    public void searchAutomobiles() {

    }

    @SuppressWarnings("unused")
    public void searchMotorcycles() {

    }

    @SuppressWarnings("unused")
    public void searchHelicopters() {

    }

    @SuppressWarnings("unused")
    public void insertVehicle() {

    }

    @SuppressWarnings("unused")
    public void updateVehicle() {

    }

    @SuppressWarnings("unused")
    public void deleteVehicle() {

    }

}
