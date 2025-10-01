package com.example.Controllers.InsertFactories;

import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Services.HelicopterServices;
import com.example.Utils.Interfaces.InsertFactory;
import org.springframework.stereotype.Component;

@Component
public class HelicopterInsertFactory implements InsertFactory<Helicopter, HelicopterServices> {

    private final HelicopterServices helicopterServices;

    public HelicopterInsertFactory(HelicopterServices helicopterServices) {
        this.helicopterServices = helicopterServices;
    }

    @Override
    public HelicopterServices getSavingService() {
        return this.helicopterServices;
    }

    
    public void start() {
        runDynamicInsertMenu(Helicopter.class);
    }
}