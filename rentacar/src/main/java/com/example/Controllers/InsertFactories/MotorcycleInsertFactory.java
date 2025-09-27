package com.example.Controllers.InsertFactories;
import com.example.DTO.MotorcycleDto;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Entities.DbModels.Vehicles.VehicleProperties;
import com.example.Services.MotorcycleServices;
import com.example.Services.VehiclePropertiesServices;
import com.example.Utils.Enums.MotorcycleMobility;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Global;
import com.example.Utils.Interfaces.InsertFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class MotorcycleInsertFactory implements InsertFactory<Motorcycle, MotorcycleDto> {

    private final MotorcycleServices motorcycleServices;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    public MotorcycleInsertFactory(MotorcycleServices motorcycleServices, VehiclePropertiesServices vehiclePropertiesServices) {
        this.motorcycleServices = motorcycleServices;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

    @Override
    public Optional<MotorcycleDto> getDtoByIdentifier(String identifier) {
        return motorcycleServices.getMotorcycleByPlateOrTailNumberAsDto(identifier);
    }

    @Override
    public Motorcycle performInsertionProcess() throws Exception {
        // Adım 1: VehicleProperties
        System.out.println("Step 1: Pricing Properties");
        System.out.print("Enter Daily Pricing: ");
        BigDecimal dailyPricing = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Weekly Pricing: ");
        BigDecimal weeklyPricing = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Monthly Pricing: ");
        BigDecimal monthlyPricing = new BigDecimal(Global.scanner.nextLine());
        
        VehicleProperties newProperties = new VehicleProperties();
        newProperties.setDailyPricing(dailyPricing);
        newProperties.setWeeklyPricing(weeklyPricing);
        newProperties.setMonthlyPricing(monthlyPricing);
        VehicleProperties savedProperties = vehiclePropertiesServices.saveNewProperties(newProperties);
        System.out.println("Pricing properties saved.");

        // Adım 2: Vehicle & Motorcycle
        System.out.println("\nStep 2: Vehicle & Motorcycle Details");
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
        System.out.print("Enter Engine Capacity (CC): ");
        int engineCC = Integer.parseInt(Global.scanner.nextLine());
        System.out.print("Enter Mobility Type (e.g., ON_ROAD, OFF_ROAD, SCOOTER): ");
        MotorcycleMobility mobilityType = MotorcycleMobility.valueOf(Global.scanner.nextLine().toUpperCase());

        Motorcycle newMotorcycle = new Motorcycle();
        newMotorcycle.setBrandName(brandName);
        newMotorcycle.setModelName(modelName);
        newMotorcycle.setModelYear(modelYear);
        newMotorcycle.setPlateOrTailNumber(plate);
        newMotorcycle.setCurrentFuel(currentFuel);
        newMotorcycle.setMaxFuelCapacity(maxFuel);
        newMotorcycle.setVehicleValue(vehicleValue);
        newMotorcycle.setVehicleStatus(VehicleStatus.AVAILABLE);
        newMotorcycle.setKm(km);
        newMotorcycle.setEngineCC(engineCC);
        newMotorcycle.setMobilityType(mobilityType);
        newMotorcycle.setProperties(savedProperties);

        return motorcycleServices.saveNewMotorcycle(newMotorcycle);
    }

    public void start() {
        runInsertMenu();
    }
}