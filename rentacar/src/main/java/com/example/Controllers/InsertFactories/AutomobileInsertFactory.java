package com.example.Controllers.InsertFactories;
import com.example.DTO.AutomobileInfoDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.DbModels.Vehicles.VehicleProperties;
import com.example.Services.AutomobileServices;
import com.example.Services.VehiclePropertiesServices;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.WheelDriveType;
import com.example.Utils.Global;
import com.example.Utils.Interfaces.InsertFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AutomobileInsertFactory implements InsertFactory<Automobile, AutomobileInfoDto> {

    private final AutomobileServices automobileServices;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    public AutomobileInsertFactory(AutomobileServices automobileServices, VehiclePropertiesServices vehiclePropertiesServices) {
        this.automobileServices = automobileServices;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }
    
    @Override
    public Optional<AutomobileInfoDto> getDtoByIdentifier(String identifier) {
        return automobileServices.getAutomobilesByPlateOrTailNumberAsInfoDto(identifier);
    }
    
    @Override
    public Automobile performInsertionProcess() throws Exception {
        // Adım 1: VehicleProperties için veri topla ve kaydet
        System.out.println("Step 1: Pricing Properties");
        System.out.print("Enter Daily Pricing (e.g., 150.00): ");
        BigDecimal dailyPricing = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Weekly Pricing (e.g., 900.50): ");
        BigDecimal weeklyPricing = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Monthly Pricing (e.g., 3500.00): ");
        BigDecimal monthlyPricing = new BigDecimal(Global.scanner.nextLine());

        VehicleProperties newProperties = new VehicleProperties();
        newProperties.setDailyPricing(dailyPricing);
        newProperties.setWeeklyPricing(weeklyPricing);
        newProperties.setMonthlyPricing(monthlyPricing);
        VehicleProperties savedProperties = vehiclePropertiesServices.saveNewProperties(newProperties);
        System.out.println("Pricing properties saved successfully.");

        // Adım 2: Vehicle ve Automobile için veri topla
        System.out.println("\nStep 2: Vehicle & Automobile Details");
        System.out.print("Enter Brand Name: ");
        String brandName = Global.scanner.nextLine();
        System.out.print("Enter Model Name: ");
        String modelName = Global.scanner.nextLine();
        System.out.print("Enter Plate: ");
        String plate = Global.scanner.nextLine();
        System.out.print("Enter Model Year: ");
        int modelYear = Integer.parseInt(Global.scanner.nextLine());
        System.out.print("Enter Market Value: ");
        int vehicleValue = Integer.parseInt(Global.scanner.nextLine());
        System.out.print("Enter Odometer (KM): ");
        BigDecimal km = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Max Fuel Capacity (Liters): ");
        BigDecimal maxFuel = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Current Fuel (Liters): ");
        BigDecimal currentFuel = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Drivetrain (FWD, RWD, AWD): ");
        WheelDriveType driveType = WheelDriveType.valueOf(Global.scanner.nextLine().toUpperCase());

        // Adım 3: Nesneleri birleştir
        Automobile newAutomobile = new Automobile();
        newAutomobile.setBrandName(brandName);
        newAutomobile.setModelName(modelName);
        newAutomobile.setModelYear(modelYear);
        newAutomobile.setPlateOrTailNumber(plate);
        newAutomobile.setCurrentFuel(currentFuel);
        newAutomobile.setMaxFuelCapacity(maxFuel);
        newAutomobile.setVehicleValue(vehicleValue);
        newAutomobile.setVehicleStatus(VehicleStatus.AVAILABLE);
        newAutomobile.setKm(km);
        newAutomobile.setWheelDriveType(driveType);
        newAutomobile.setProperties(savedProperties);

        // Adım 4: Servisi kullanarak kaydet ve kaydedilmiş nesneyi döndür
        return automobileServices.saveNewAutomobile(newAutomobile);
    }

    public void start() {
        runInsertMenu();
    }
}
