package com.example.Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.Controllers.SelectControllers.AutomobileSelectController;
import com.example.Controllers.UpdateControllers.AutomobileUpdateController;
import com.example.DTO.AutomobileDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.DbModels.Vehicles.VehicleProperties;
import com.example.Services.AutomobileServices;
import com.example.Services.VehiclePropertiesServices;

import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Enums.VehicleStatus;

import com.example.Utils.Enums.WheelDriveType;
import com.example.Utils.Interfaces.Controller;

@Component
public class AutomobileController implements Controller {

    private final AutomobileServices automobileServices;
    private final AutomobileUpdateController automobileUpdateController;
    private final AutomobileSelectController automobileSelectController;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    public AutomobileController(AutomobileServices automobileServices,
            AutomobileUpdateController automobileUpdateController,
            AutomobileSelectController automobileSelectController, // SelectController eklendi
            VehiclePropertiesServices vehiclePropertiesServices) {
        this.automobileServices = automobileServices;
        this.automobileUpdateController = automobileUpdateController;
        this.automobileSelectController = automobileSelectController; // SelectController eklendi
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

    @Override
    public void start() {
        runMenuLoop("Automobile Services");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        // Menü başlıkları daha anlaşılır hale getirildi.
        List<String> menuCases = new ArrayList<>(Arrays.asList("List All Automobiles", "Search Automobile by Field"));

        if (Global.currentUser != null && Global.currentUser.getRole() == UserRoles.ADMIN) {
            menuCases.add("Insert New Automobile");
            menuCases.add("Update Existing Automobile");
            menuCases.add("Delete Automobile");
        }
        menuCases.add("Exit");
        return menuCases;
    }

    public void listAllAutomobiles() {
        System.out.println("\n--- All Automobiles in the System ---");
        List<AutomobileDto> automobileList = automobileServices.getAllAutomobilesAsDto();
        if (automobileList.isEmpty()) {
            System.out.println("No automobiles found in the system.");
        } else {
            automobileList.forEach(System.out::println);
        }
    }

    public void searchAutomobileByField() {
        // Arama işlemi artık tamamen SelectController'a devredildi.
        automobileSelectController.start();
    }

    public void insertNewAutomobile() {
        // Eski setFields() metodunun tüm mantığı buraya taşındı.
        // Gelecekte bu metodun içeriği, bir InsertController'a kolayca taşınabilir.
        System.out.println("\n--- Inserting New Automobile ---");
        try {

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
            System.out.println("Pricing properties saved successfully with ID: " + savedProperties.getPropId());
            newAutomobile.setProperties(savedProperties);

            Automobile savedAutomobile = automobileServices.saveNewAutomobile(newAutomobile);

            System.out.println("\n--- SUCCESS ---");
            System.out.println("New automobile has been saved successfully!");
            Optional<AutomobileDto> dto = automobileServices
                    .getAutomobileByPlateOrTailNumberAsDto(savedAutomobile.getPlateOrTailNumber());
            dto.ifPresent(System.out::println);

        } catch (NumberFormatException e) {
            System.out.println("\n--- ERROR --- \nInvalid number format. Operation cancelled.");
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "\n--- ERROR --- \nInvalid input for Drivetrain. Use FWD, RWD, or AWD. Operation cancelled.");
        } catch (Exception e) {
            System.out.println("\n--- ERROR --- \nAn unexpected error occurred: " + e.getMessage());
        }
    }

    public void updateExistingAutomobile() {
        System.out.println("\n--- Update an Existing Automobile ---");
        List<AutomobileDto> automobileList = automobileServices.getAllAutomobilesAsDto();

        if (automobileList.isEmpty()) {
            System.out.println("There are no automobiles in the system to update.");
            return;
        }

        automobileList.forEach(System.out::println);
        System.out.print("\nPlease enter the ID of the automobile you want to update: ");

        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            automobileUpdateController.start(selectedId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void deleteAutomobile() {
        // TODO: Implement delete logic
        System.out.println("Delete functionality is not yet implemented.");
    }
}
