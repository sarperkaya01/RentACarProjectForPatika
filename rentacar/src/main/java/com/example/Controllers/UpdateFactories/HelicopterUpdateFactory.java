package com.example.Controllers.UpdateFactories;

import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Services.HelicopterServices;
import com.example.Utils.Interfaces.UpdateFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class HelicopterUpdateFactory implements UpdateFactory<Helicopter, HelicopterServices> {

    private final HelicopterServices helicopterServices;

    public HelicopterUpdateFactory(HelicopterServices helicopterServices) {
        this.helicopterServices = helicopterServices;
    }

    @Override
    public HelicopterServices getUpdateService() {
        return this.helicopterServices;
    }

    public void start(Integer helicopterId) {
        runUpdateMenu(helicopterId, Helicopter.class);
    }

    @Override
    public List<String> getAdditionalUpdatableFields() {
        return Arrays.asList("dailyPricing", "weeklyPricing", "monthlyPricing");
    }
}
