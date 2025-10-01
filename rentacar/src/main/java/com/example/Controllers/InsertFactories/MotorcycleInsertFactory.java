package com.example.Controllers.InsertFactories;

import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Services.MotorcycleServices;
import com.example.Utils.Interfaces.InsertFactory;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleInsertFactory implements InsertFactory<Motorcycle, MotorcycleServices> {

    private final MotorcycleServices motorcycleService;

    public MotorcycleInsertFactory(MotorcycleServices motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @Override
    public MotorcycleServices getSavingService() {
        return this.motorcycleService;
    }

    public void start() {
        runDynamicInsertMenu(Motorcycle.class);
    }
}