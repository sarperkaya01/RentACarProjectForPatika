package com.example.Controllers.InsertFactories;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Services.AutomobileServices;
import com.example.Services.VehicleRegistrationService;
import com.example.Utils.Interfaces.InsertFactory;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AutomobileInsertFactory implements InsertFactory<Automobile, VehicleRegistrationService> {

    private final VehicleRegistrationService vehicleRegistrationService;

    public AutomobileInsertFactory(VehicleRegistrationService vehicleRegistrationService) {
        this.vehicleRegistrationService = vehicleRegistrationService;
    }

    @Override
    public VehicleRegistrationService getSavingService() {
        return this.vehicleRegistrationService;
    }

    @Override
    public String getSaveMethodName(Class<Automobile> entityType) {

        return "-";
    }

    @Override
    public List<String> getFieldsToSkip() {

        return Arrays.asList("id", "vehicleId", "pricing", "vehicleType");
    }

    public void start() {
        // Sadece bu metodu çağırmamız yeterli olacak!
        Automobile a = runDynamicInsertMenu(Automobile.class);

        try {
            vehicleRegistrationService.registerNewAutomobile(a);
            System.out.println("New car added !");
        } catch (Exception e) {
            System.out.println("Add operation failed: " + e.getMessage());
            // Burada hata olduğunda, veritabanına hiçbir şey yazılmadığından emin
        }
    }
}
