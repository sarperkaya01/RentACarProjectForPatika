package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.DTO.RentalListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Services.CheckoutServices;
import com.example.Services.CustomerServices;
import com.example.Services.RentalServices;
import com.example.Services.VehicleService;
import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;
import com.example.Utils.Interfaces.SummarizableController;

@Component
public class RentalsController implements Controller, SummarizableController<RentalListDto> {

    private final RentalServices rentalService;
    private final VehicleOperationsController vehicleOpertionsController;
    private final VehicleService vehicleService;
    private final CustomerServices customerService;
    private final CheckoutServices checkoutServices;

    public RentalsController(RentalServices rentalService, VehicleOperationsController vehicleOpertionsController,
            VehicleService vehicleService, CustomerServices customerService, CheckoutServices checkoutServices) {
        this.rentalService = rentalService;
        this.vehicleOpertionsController = vehicleOpertionsController;
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.checkoutServices = checkoutServices;
    }

    @Override
    public void start() {
        runMenuLoop("Rentals Menu");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {

        List<String> menuCases = new ArrayList<>(Arrays.asList("Rent", "Finalize a Rental"));
        if (Global.currentUser != null && Global.currentUser.getRole() == UserRoles.ADMIN) {
            menuCases.add("List All Rentals");
            menuCases.add("Search For a Rental");
        }
        menuCases.add("Exit");
        return menuCases;
    }

    public void rent() {
		System.out.println("Vehicle ID (Please press 'Enter' if you don't):");
        Integer input = Global.scanner.nextInt();
        if (input != null) {
            vehicleOpertionsController.start();
        }
        Vehicle v = vehicleService.getVehicleById(input);
        Customer c = customerService.getCustomerById(Global.currentUser.getUserId());
        


        
	}

    public void finalizeARental() {
        // TODO: Implement method
    }

    public void listAllRentals() {
        listAllSummary();
    }

    public void searchForARental() {
        // TODO: Implement method
    }

    @Override
    public Supplier<List<RentalListDto>> getSummaryDtoListSupplier() {
        return () -> rentalService.getAllRentalsAsListDto();
    }

    @Override
    public String getEntityName() {
        return "Rental";
    }
}
