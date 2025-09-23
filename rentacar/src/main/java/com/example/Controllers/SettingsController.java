package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;

public class SettingsController implements Controller {

    private final VehicleOperationsController voc;

    public SettingsController(VehicleOperationsController voc) {
        this.voc = voc;
    }

    @Override
    public void start() {
        runMenuLoop("Settings");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        List<String> menuCases = new ArrayList<>(Arrays.asList("Vehicle Operations"));

        if (Global.currentUser != null) {
            if (Global.currentUser.getRole() == UserRoles.ADMIN) {// admin icin rental operations ekleyebilirim
                menuCases.add("User Maneger");
            }
        }

        menuCases.add("Exit");

        return menuCases;
    }

    @SuppressWarnings("unused")
    public void vehicleOperations() {
        voc.start();

    }

    @SuppressWarnings("unused")
    public void userManeger() {

    }

}
