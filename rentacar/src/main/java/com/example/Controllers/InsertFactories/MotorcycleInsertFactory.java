package com.example.Controllers.InsertFactories;


import com.example.Entities.DbModels.Vehicles.Motorcycle;

import com.example.Services.VehicleRegistrationService;
import com.example.Utils.Interfaces.InsertFactory;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MotorcycleInsertFactory implements InsertFactory<Motorcycle, VehicleRegistrationService> {

    private final VehicleRegistrationService vehicleRegistrationService;

    public MotorcycleInsertFactory(VehicleRegistrationService vehicleRegistrationService) {
        this.vehicleRegistrationService = vehicleRegistrationService;
    }


    @Override
    public VehicleRegistrationService getSavingService() {
        return this.vehicleRegistrationService;
    }

    @Override
    public String getSaveMethodName(Class<Motorcycle> entityType) {

        return "-";
    }
    @Override
    public List<String> getFieldsToSkip() {

        return Arrays.asList("id", "vehicleId", "pricing", "vehicleType");
    }

    public void start() {
        Motorcycle m = runDynamicInsertMenu(Motorcycle.class);
        try {
            vehicleRegistrationService.registerNewMotorcycle(m);
            System.out.println("New car added !");
        } catch (Exception e) {
            System.out.println("Add operation failed: " + e.getMessage());
            // Burada hata olduğunda, veritabanına hiçbir şey yazılmadığından emin
        }
    }
}