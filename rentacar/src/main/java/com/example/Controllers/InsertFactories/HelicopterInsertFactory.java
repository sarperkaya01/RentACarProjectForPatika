package com.example.Controllers.InsertFactories;

import com.example.DTO.HelicopterInfoDto;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Entities.DbModels.Vehicles.VehicleProperties;
import com.example.Services.HelicopterServices;
import com.example.Services.VehiclePropertiesServices;
import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Global;
import com.example.Utils.Interfaces.InsertFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class HelicopterInsertFactory implements InsertFactory<Helicopter, HelicopterInfoDto> {

    private final HelicopterServices helicopterServices;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    public HelicopterInsertFactory(HelicopterServices helicopterServices, VehiclePropertiesServices vehiclePropertiesServices) {
        this.helicopterServices = helicopterServices;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

   @Override
    public Optional<HelicopterInfoDto> getDtoByIdentifier(String identifier) {
        return helicopterServices.getHelicopterByPlateOrTailNumberAsInfoDto(identifier);
    }

    @Override
    public Helicopter performInsertionProcess() throws Exception {
        // Step 1: VehicleProperties
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

        // Step 2: Vehicle & Helicopter
        System.out.println("\nStep 2: Vehicle & Helicopter Details");
        System.out.print("Enter Brand Name: ");
        String brandName = Global.scanner.nextLine();
        System.out.print("Enter Model Name: ");
        String modelName = Global.scanner.nextLine();
        System.out.print("Enter Tail Number: ");
        String tailNumber = Global.scanner.nextLine();
        System.out.print("Enter Model Year: ");
        int modelYear = Integer.parseInt(Global.scanner.nextLine());
        System.out.print("Enter Market Value: ");
        int vehicleValue = Integer.parseInt(Global.scanner.nextLine());
        System.out.print("Enter Flight Hours: ");
        BigDecimal flightHours = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Max Fuel Capacity (Liters): ");
        BigDecimal maxFuel = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Current Fuel (Liters): ");
        BigDecimal currentFuel = new BigDecimal(Global.scanner.nextLine());
        System.out.print("Enter Speciality (e.g., CARGO, MEDICAL, TRANSPORT): ");
        HeliSpeciality speciality = HeliSpeciality.valueOf(Global.scanner.nextLine().toUpperCase());

        Helicopter newHelicopter = new Helicopter();
        newHelicopter.setBrandName(brandName);
        newHelicopter.setModelName(modelName);
        newHelicopter.setModelYear(modelYear);
        newHelicopter.setPlateOrTailNumber(tailNumber);
        newHelicopter.setCurrentFuel(currentFuel);
        newHelicopter.setMaxFuelCapacity(maxFuel);
        newHelicopter.setVehicleValue(vehicleValue);
        newHelicopter.setVehicleStatus(VehicleStatus.AVAILABLE);
        newHelicopter.setFlightHours(flightHours);
        newHelicopter.setSpeciality(speciality);
        newHelicopter.setProperties(savedProperties);

        return helicopterServices.saveNewHelicopter(newHelicopter);
    }

    public void start() {
        runInsertMenu();
    }
}
