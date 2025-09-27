package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.Controllers.InsertFactories.MotorcycleInsertFactory;
import com.example.Controllers.SelectFactories.MotorcycleSelectFactory;
import com.example.Controllers.UpdateFactories.MotorcycleUpdateFactory;
import com.example.DTO.VehicleListDto;
import com.example.Services.MotorcycleServices;
import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;
import com.example.Utils.Interfaces.SummarizableController;

@Component
public class MotorcycleController implements Controller, SummarizableController<VehicleListDto> {

    private final MotorcycleServices motorcycleServices;
    private final MotorcycleUpdateFactory motorcycleUpdateFactory;
    private final MotorcycleSelectFactory motorcycleSelectFactory;
    private final MotorcycleInsertFactory motorcycleInsertController;

    public MotorcycleController(MotorcycleServices motorcycleServices, MotorcycleUpdateFactory motorcycleUpdateFactory,
            MotorcycleSelectFactory motorcycleSelectFactory, MotorcycleInsertFactory motorcycleInsertController) {
        this.motorcycleServices = motorcycleServices;
        this.motorcycleUpdateFactory = motorcycleUpdateFactory;
        this.motorcycleSelectFactory = motorcycleSelectFactory;
        this.motorcycleInsertController = motorcycleInsertController;
    }

    @Override
    public void start() {
        runMenuLoop("Motorcycle Services");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        List<String> menuCases = new ArrayList<>(
                Arrays.asList("List All Motorcycles", "Search Motorcycle by Field"));
        if (Global.currentUser != null && Global.currentUser.getRole() == UserRoles.ADMIN) {
            menuCases.add("Insert New Motorcycle");
            menuCases.add("Update Existing Motorcycle");
            menuCases.add("Delete Motorcycle");
        }
        menuCases.add("Exit");
        return menuCases;
    }

    @Override
    public Supplier<List<VehicleListDto>> getSummaryDtoListSupplier() {
        return () -> motorcycleServices.getAllMotorcyclesAsSummaryDto();
    }

    @Override
    public String getEntityName() {
        return "Motorcycle";
    }

    // --- Menü Metotları ---

    public void listAllMotorcycles() {
        listAllSummary();
    }

    public void searchMotorcycleByField() {
        motorcycleSelectFactory.start();
    }

    public void insertNewMotorcycle() {
        motorcycleInsertController.start();
    }

    public void updateExistingMotorcycle() {
        System.out.println("\n--- Update an Existing Motorcycle ---");
        listAllSummary(); // Önce özet listeyi göster

        System.out.print("\nPlease enter the ID of the motorcycle you want to update: ");
        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            motorcycleUpdateFactory.start(selectedId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    public void deleteMotorcycle() {
        System.out.println("\n--- Delete an Existing Motorcycle ---");
        listAllSummary(); // Önce özet listeyi göster

        System.out.print("\nPlease enter the ID of the motorcycle you want to delete: ");
        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            motorcycleServices.deleteMotorcycle(selectedId);
            System.out.println("\n--- SUCCESS --- \nMotorcycle with ID " + selectedId + " has been deleted.");
        } catch (NumberFormatException e) {
            System.out.println("\n--- ERROR --- \nInvalid ID format. Deletion cancelled.");
        } catch (Exception e) {
            System.out.println("\n--- ERROR --- \n" + e.getMessage());
        }
    }

}
