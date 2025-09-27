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
    private final MotorcycleController motorcycleController;
    private final HelicopterController helicopterController;

    @Lazy
    @Autowired
    public VehicleOperationsController(AutomobileController automobileController,
            MotorcycleController motorcycleController, HelicopterController helicopterController) {
        this.automobileController = automobileController;
        this.motorcycleController = motorcycleController;
        this.helicopterController = helicopterController;
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

    public void automobile() {
        automobileController.start();

    }

    public void motorcycle() {
        motorcycleController.start();
    }

    public void helicopter() {
        helicopterController.start();
    }

}
