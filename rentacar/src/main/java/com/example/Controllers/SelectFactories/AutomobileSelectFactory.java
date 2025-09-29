package com.example.Controllers.SelectFactories;

import com.example.DTO.AutomobileInfoDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Services.AutomobileServices;
import com.example.Utils.Interfaces.SelectFactory;
import org.springframework.stereotype.Component;

@Component
public class AutomobileSelectFactory implements SelectFactory<Automobile, AutomobileInfoDto, AutomobileServices> {

    private final AutomobileServices automobileServices;

    public AutomobileSelectFactory(AutomobileServices automobileServices) {
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