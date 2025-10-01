package com.example.Controllers.InsertFactories;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.Renting.Checkout;
import com.example.Services.VehicleRegistrationService;
import com.example.Utils.Interfaces.InsertFactory;
@Component
public class CheckoutInsertFactory implements InsertFactory<Checkout, VehicleRegistrationService>{

    private final VehicleRegistrationService vehicleRegistrationService;

    public CheckoutInsertFactory(VehicleRegistrationService vehicleRegistrationService) {
        this.vehicleRegistrationService = vehicleRegistrationService;
    }
   
    @Override
    public VehicleRegistrationService getSavingService() {
        return this.vehicleRegistrationService;
    }

    @Override
    public String getSaveMethodName(Class<Checkout> entityType) {

        return "-";
    }

    @Override
    public List<String> getFieldsToSkip() {

        return Arrays.asList("id", "vehicleId", "pricing", "vehicleType");
    }

    public void start() {
        // Sadece bu metodu çağırmamız yeterli olacak!
        Checkout a = runDynamicInsertMenu(Checkout.class);

        // try {
        //     vehicleRegistrationService.registerNewAutomobile(a);
        //     System.out.println("New car added !");
        // } catch (Exception e) {
        //     System.out.println("Add operation failed: " + e.getMessage());
        //     // Burada hata olduğunda, veritabanına hiçbir şey yazılmadığından emin
        // }
    }

}
