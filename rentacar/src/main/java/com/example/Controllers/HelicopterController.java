package com.example.Controllers;

import com.example.Controllers.InsertFactories.HelicopterInsertFactory;
import com.example.Controllers.SelectFactories.HelicopterSelectFactory;
import com.example.Controllers.UpdateFactories.HelicopterUpdateFactory;
import com.example.DTO.VehicleListDto;
import com.example.Services.HelicopterServices;
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
public class HelicopterController implements Controller, SummarizableController<VehicleListDto> {

    private final HelicopterServices helicopterServices;
    private final HelicopterUpdateFactory helicopterUpdateFactory;
    private final HelicopterSelectFactory helicopterSelectFactory;
    private final HelicopterInsertFactory helicopterInsertFactory;

    public HelicopterController(HelicopterServices helicopterServices,
            HelicopterUpdateFactory helicopterUpdateFactory,
            HelicopterSelectFactory helicopterSelectFactory,
            HelicopterInsertFactory helicopterInsertFactory) {
        this.helicopterServices = helicopterServices;
        this.helicopterUpdateFactory = helicopterUpdateFactory;
        this.helicopterSelectFactory = helicopterSelectFactory;
        this.helicopterInsertFactory = helicopterInsertFactory;
    }

    @Override
    public void start() {
        runMenuLoop("Helicopter Services");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        List<String> menuCases = new ArrayList<>(
                Arrays.asList("List All Helicopters", "Search Helicopter by Field"));
        if (Global.currentUser != null && Global.currentUser.getRole() == UserRoles.ADMIN) {
            menuCases.add("Insert New Helicopter");
            menuCases.add("Update Existing Helicopter");
            menuCases.add("Delete Helicopter");
        }
        menuCases.add("Exit");
        return menuCases;
    }

    @Override
    public Supplier<List<VehicleListDto>> getSummaryDtoListSupplier() {
        return () -> helicopterServices.getAllHelicoptersAsSummaryDto();
    }

    @Override
    public String getEntityName() {
        return "Helicopter";
    }

    public void listAllHelicopters() {
        listAllSummary();
    }

    public void searchHelicopterByField() {
        helicopterSelectFactory.start();
    }

    public void insertNewHelicopter() {
        helicopterInsertFactory.start();
    }

    public void updateExistingHelicopter() {
        System.out.println("\n--- Update an Existing Helicopter ---");
        listAllSummary();

        System.out.print("\nPlease enter the ID of the helicopter you want to update: ");
        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            helicopterUpdateFactory.start(selectedId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    public void deleteHelicopter() {
        System.out.println("\n--- Delete an Existing Helicopter ---");
        listAllSummary();

        System.out.print("\nPlease enter the ID of the helicopter you want to delete: ");
        try {
            int selectedId = Integer.parseInt(Global.scanner.nextLine());
            helicopterServices.deleteHelicopter(selectedId);
            System.out.println("\n--- SUCCESS --- \nHelicopter with ID " + selectedId + " has been deleted.");
        } catch (NumberFormatException e) {
            System.out.println("\n--- ERROR --- \nInvalid ID format. Deletion cancelled.");
        } catch (Exception e) {
            System.out.println("\n--- ERROR --- \n" + e.getMessage());
        }
    }
}