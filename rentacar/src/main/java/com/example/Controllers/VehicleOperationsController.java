package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import com.example.Utils.Interfaces.Controller;

@Component
public class VehicleOperationsController implements Controller {
    private final AutomobileController automobileController;

    @Lazy
    @Autowired
    public VehicleOperationsController(AutomobileController automobileController) {
        this.automobileController = automobileController;
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
        List<String> menuCases = new ArrayList<>(Arrays.asList("Automobile", "Motorcycle", "Helicopter"));

      

        menuCases.add("Exit");

        return menuCases;
    }

    @SuppressWarnings("unused")
    public void automobile() {
        automobileController.start();

    }

    @SuppressWarnings("unused")
    public void motorcycle() {

    }

    @SuppressWarnings("unused")
    public void helicopter() {

    }


}
