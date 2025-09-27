package com.example.Controllers.UpdateFactories;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Services.AutomobileServices;
import com.example.Utils.Interfaces.UpdateFactory;

@Component
public class AutomobileUpdateFactory implements UpdateFactory<Automobile, AutomobileServices> {

    private final AutomobileServices automobileServices;

    public AutomobileUpdateFactory(AutomobileServices automobileServices) {
        this.automobileServices = automobileServices;
    }

    @Override
    public AutomobileServices getUpdateService() {

        return this.automobileServices;
    }

    public void start(Integer automobileId) {

        runUpdateMenu(automobileId, Automobile.class);
    }

    @Override
    public List<String> getAdditionalUpdatableFields() { // <-- İSİM DÜZELTİLDİ
        return Arrays.asList("dailyPricing", "weeklyPricing", "monthlyPricing");
    }

}
