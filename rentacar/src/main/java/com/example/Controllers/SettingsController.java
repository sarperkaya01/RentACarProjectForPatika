package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.Utils.Interfaces.Controller;

public class SettingsController implements Controller {

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        List<String> menuCases = new ArrayList<>(Arrays.asList("Vehicles", "Rentals"));

        menuCases.add("Exit");

        return menuCases;
    }

}
