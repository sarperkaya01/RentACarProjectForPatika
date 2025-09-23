package com.example.Controllers;

import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Services.AutomobileServices;
import com.example.Utils.Interfaces.UpdateController;
@Component
public class AutomobileController implements UpdateController<Automobile,AutomobileServices>{

    private final AutomobileServices automobileServices;
    private Automobile auto;

    

    public AutomobileController(AutomobileServices automobileServices, Automobile auto) {
        this.automobileServices = automobileServices;
        this.auto = auto;
    }
    @Override
    public AutomobileServices getUpdateService() {
        return this.automobileServices;
    }
    @Override
    public void start(){
        runUpdateMenu(auto.getId(), Automobile.class, "Update Automobile (Auto-Generated Menu)");
    }

}
