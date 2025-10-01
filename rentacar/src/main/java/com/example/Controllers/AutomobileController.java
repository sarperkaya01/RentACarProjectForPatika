package com.example.Controllers;

import com.example.Controllers.InsertFactories.AutomobileInsertFactory;
import com.example.Controllers.SelectFactories.AutomobileSelectFactory;
import com.example.Controllers.UpdateFactories.AutomobileUpdateFactory;

import com.example.DTO.VehicleListDto;
import com.example.Services.AutomobileServices;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Global;
import com.example.Utils.Interfaces.Controller;
import com.example.Utils.Interfaces.SummarizableController;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Component

public class AutomobileController implements Controller, SummarizableController<VehicleListDto> {

    private final AutomobileServices automobileServices;
    private final AutomobileUpdateFactory automobileUpdateFactory;
    private final AutomobileSelectFactory automobileSelectFactory;
    private final AutomobileInsertFactory automobileInsertController;

    public AutomobileController(AutomobileServices automobileServices,
            AutomobileUpdateFactory automobileUpdateFactory,
            AutomobileSelectFactory automobileSelectFactory,
            AutomobileInsertFactory automobileInsertController) {
        this.automobileServices = automobileServices;
        this.automobileUpdateFactory = automobileUpdateFactory;
        this.automobileSelectFactory = automobileSelectFactory;
        this.automobileInsertController = automobileInsertController;
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
        List<String> menuCases = new ArrayList<>(Arrays.asList("List All Automobiles", "Search Automobile by Field"));
        if (Global.currentUser != null && Global.currentUser.getRole() == UserRoles.ADMIN) {
            menuCases.add("Insert New Automobile");
            menuCases.add("Update Existing Automobile");
            menuCases.add("Delete Automobile");
        }
        menuCases.add("Exit");
        return menuCases;
    }

    @Override
    public Supplier<List<VehicleListDto>> getSummaryDtoListSupplier() {
        return () -> automobileServices.getAllAutomobilesAsListDto(); // <-- HIZLI METOT
    }

    @Override
    public String getEntityName() {
        return "Automobile";
    }

    public void listAllAutomobiles() {
        listAllSummary();
    }

    public void searchAutomobileByField() {
        automobileSelectFactory.start();
    }

    public void insertNewAutomobile() {
        automobileInsertController.start();
    }

    public void updateExistingAutomobile() {
        System.out.println("\n--- Update an Existing Automobile ---");

        listAllSummary(); // Özet listeyi ekrana basar.

        // Sistemde araç olup olmadığını kontrol etmek için yeni, hızlı metodu kullan.
        List<VehicleListDto> vehicleList = automobileServices.getAllAutomobilesAsListDto();
        if (vehicleList.isEmpty()) {
            System.out.println("There are no automobiles in the system to update.");
            return;
        }

        System.out.print("\nPlease enter the ID of the automobile you want to update: ");
        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            automobileUpdateFactory.start(selectedId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void deleteAutomobile() {
        System.out.println("\n--- Delete an Existing Automobile ---");

        listAllSummary(); // Özet listeyi ekrana basar.

        // Sistemde araç olup olmadığını kontrol etmek için yeni, hızlı metodu kullan.
        List<VehicleListDto> vehicleList = automobileServices.getAllAutomobilesAsListDto();
        if (vehicleList.isEmpty()) {
            System.out.println("There are no automobiles in the system to delete.");
            return;
        }

        System.out.print("\nPlease enter the ID of the automobile you want to delete: ");
        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            automobileServices.deleteAutomobile(selectedId);
            System.out.println(
                    "\n--- SUCCESS --- \nAutomobile with ID " + selectedId + " has been successfully deleted.");
        } catch (NumberFormatException e) {
            System.out.println("\n--- ERROR --- \nInvalid ID format. Deletion cancelled.");
        } catch (Exception e) {
            System.out.println("\n--- ERROR --- \n" + e.getMessage());
        }
    }
}
