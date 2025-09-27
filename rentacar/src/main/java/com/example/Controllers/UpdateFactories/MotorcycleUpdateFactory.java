package com.example.Controllers.UpdateFactories;

import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Services.MotorcycleServices;
import com.example.Utils.Interfaces.UpdateFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MotorcycleUpdateFactory implements UpdateFactory<Motorcycle, MotorcycleServices> {

    private final MotorcycleServices motorcycleServices;

    public MotorcycleUpdateFactory(MotorcycleServices motorcycleServices) {
        this.motorcycleServices = motorcycleServices;
    }

    @Override
    public MotorcycleServices getUpdateService() {
        return this.motorcycleServices;
    }

    public void start(Integer motorcycleId) {
        runUpdateMenu(motorcycleId, Motorcycle.class);
    }

    @Override
    public List<String> getAdditionalUpdatableFields() {
        return Arrays.asList("dailyPricing", "weeklyPricing", "monthlyPricing");
    }
}