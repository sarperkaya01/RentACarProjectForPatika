package com.example.Controllers.SelectControllers;

import com.example.DTO.AutomobileDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Services.AutomobileServices;
import com.example.Utils.Interfaces.SelectFactory;
import org.springframework.stereotype.Component;

@Component
public class AutomobileSelectController implements SelectFactory<Automobile, AutomobileDto, AutomobileServices> {

    private final AutomobileServices automobileServices;

    public AutomobileSelectController(AutomobileServices automobileServices) {
        this.automobileServices = automobileServices;
    }

    @Override
    public AutomobileServices getSelectService() {
        return this.automobileServices;
    }

    public void start() {
        runSelectMenu(Automobile.class);
    }
}