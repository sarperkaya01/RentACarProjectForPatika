package com.example.Controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Utils.Interfaces.Controller;
@Component
public class VehiclesController implements Controller {
    private MainMenuController mmc;

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void exit() {
        mmc.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Vehicle List", "Add new vehicle", "Edit a vehicle", "Exit");
    }
     private void list() {
        

    }

    private void newVehicle() {
       
    }

    private void editVehicle() {


    }
    

}
