package com.example.Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

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
    private final VehiclePropertiesServices vehiclePropertiesServices;

    public AutomobileController(AutomobileServices automobileServices,
            AutomobileUpdateController automobileUpdateController, 
            VehiclePropertiesServices vehiclePropertiesServices) {
        this.automobileServices = automobileServices;
        this.automobileUpdateController = automobileUpdateController;
        
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
        List<String> menuCases = new ArrayList<>(Arrays.asList("Search"));

        if (Global.currentUser != null) {
            if (Global.currentUser.getRole() == UserRoles.ADMIN) {
                menuCases.add("Insert Automobile");
                menuCases.add("Update Automobile");
                menuCases.add("Delete Automobile");
            }
        }

        menuCases.add("Exit");

        return menuCases;
    }

    public void search() {
        // TODO: Implement method
    }

    public void insertAutomobile() {
        Automobile a = new Automobile();
        automobileServices.saveNewAutomobile(a);
    }

    public void updateAutomobile() {

        List<AutomobileDto> ad = automobileServices.getAllAutomobilesAsDto();

        for (AutomobileDto automobileDto : ad) {
            System.out.println(automobileDto);
        }
        System.out.println("Please insert an id for whitch one do you want to update ?");
        int selectID = Global.scanner.nextInt();

        automobileUpdateController.start(selectID);

        // TODO: Implement method
    }

    public void deleteAutomobile() {
        // TODO: Implement method
    }

    public void setFields() {
        System.out.println("\n--- Inserting New Automobile ---");
        try {
            // --- 1. Adım: VehicleProperties için veri topla ---
            System.out.println("First, let's set the pricing properties...");

            System.out.print("Enter Daily Pricing (e.g., 150.00): ");
            BigDecimal dailyPricing = new BigDecimal(Global.scanner.nextLine());

            System.out.print("Enter Weekly Pricing (e.g., 900.50): ");
            BigDecimal weeklyPricing = new BigDecimal(Global.scanner.nextLine());

            System.out.print("Enter Monthly Pricing (e.g., 3500.00): ");
            BigDecimal monthlyPricing = new BigDecimal(Global.scanner.nextLine());

            // VehicleProperties nesnesini oluştur ve kaydet.
            VehicleProperties newProperties = new VehicleProperties();
           
            newProperties.setDailyPricing(dailyPricing);
            newProperties.setWeeklyPricing(weeklyPricing);
            newProperties.setMonthlyPricing(monthlyPricing);
            // Diğer fiyatlandırma alanlarını da (hourly vb.) istersen sorabilirsin.

            // Servisi kullanarak VehicleProperties'i veritabanına kaydet.
            VehicleProperties savedProperties = vehiclePropertiesServices.saveNewProperties(newProperties);
            System.out.println("Pricing properties saved successfully with ID: " + savedProperties.getPropId());

            // --- 2. Adım: Vehicle ve Automobile için veri topla ---
            System.out.println("\nNow, let's enter the vehicle's details...");

            System.out.print("Enter Brand Name (e.g., Ford): ");
            String brandName = Global.scanner.nextLine();

            System.out.print("Enter Model Name (e.g., Mustang): ");
            String modelName = Global.scanner.nextLine();

            System.out.print("Enter Plate (e.g., 34 ABC 123): ");
            String plate = Global.scanner.nextLine();

            System.out.print("Enter Model Year (e.g., 2021): ");
            int modelYear = Integer.parseInt(Global.scanner.nextLine());

            System.out.print("Enter Market Value (e.g., 500000): ");
            int vehicleValue = Integer.parseInt(Global.scanner.nextLine());

            System.out.print("Enter Odometer (KM, e.g., 55000.0): ");
            BigDecimal km = new BigDecimal(Global.scanner.nextLine());

            System.out.print("Enter Max Fuel Capacity (Liters, e.g., 60.0): ");
            BigDecimal maxFuel = new BigDecimal(Global.scanner.nextLine());

            System.out.print("Enter Current Fuel (Liters, e.g., 45.5): ");
            BigDecimal currentFuel = new BigDecimal(Global.scanner.nextLine());

            System.out.print("Enter Drivetrain (FWD, RWD, AWD): ");
            WheelDriveType driveType = WheelDriveType.valueOf(Global.scanner.nextLine().toUpperCase());

            // --- 3. Adım: Nesneleri Birleştir ve Kaydet ---
            Automobile newAutomobile = new Automobile();

            // Vehicle'dan gelen alanlar
            newAutomobile.setBrandName(brandName);
            newAutomobile.setModelName(modelName);
            newAutomobile.setModelYear(modelYear);
            newAutomobile.setVehicleValue(vehicleValue);
            newAutomobile.setVehicleStatus(VehicleStatus.AVAILABLE); // Yeni eklenen araç genellikle müsaittir.

            // Automobile'a özel alanlar
            newAutomobile.setPlate(plate);
            newAutomobile.setKm(km);
            newAutomobile.setMaxFuelCapacity(maxFuel);
            newAutomobile.setCurrentFuel(currentFuel);
            newAutomobile.setWheelDriveType(driveType);

            // En önemli adım: Az önce kaydettiğimiz properties nesnesini bu araca bağla.
            newAutomobile.setProperties(savedProperties);

            // Son olarak, tam olarak doldurulmuş Automobile nesnesini servisi kullanarak
            // kaydet.
            Automobile savedAutomobile = automobileServices.saveNewAutomobile(newAutomobile);

            System.out.println("\n--- SUCCESS ---");
            System.out.println("New automobile has been saved successfully!");
            // İstersen burada kaydedilen arabanın detaylarını DTO ile gösterebilirsin.
            System.out.println(automobileServices.getAutomobileByPlateAsDto(savedAutomobile.getPlate()).get());

        } catch (NumberFormatException e) {
            System.out.println("\n--- ERROR ---");
            System.out.println(
                    "Invalid number format. Please make sure to enter numbers correctly (e.g., 2021 for year, 150.00 for price).");
            System.out.println("Operation cancelled.");
        } catch (IllegalArgumentException e) {
            System.out.println("\n--- ERROR ---");
            System.out.println("Invalid input. For Drivetrain, please use one of: FWD, RWD, AWD.");
            System.out.println("Operation cancelled. Details: " + e.getMessage());
        } catch (Exception e) {
            // Servisten gelebilecek 'Plate already taken' gibi diğer tüm hataları yakalar.
            System.out.println("\n--- ERROR ---");
            System.out.println("An unexpected error occurred while saving the automobile.");
            System.out.println("Details: " + e.getMessage());
        }
    }
}
