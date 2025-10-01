package com.example.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.DTO.RentalListDto;
import com.example.Services.RentalServices;
import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;
import com.example.Utils.Interfaces.SummarizableController;

@Component
public class RentalsController implements Controller , SummarizableController<RentalListDto>{

    private final RentalServices rentalService; // RentalService bağımlılığı eklendi

    public RentalsController(RentalServices rentalService) {
        this.rentalService = rentalService;
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

        List<String> menuCases = new ArrayList<>(Arrays.asList("Create New Rental", "Finalize a Rental"));
        if (Global.currentUser != null && Global.currentUser.getRole() == UserRoles.ADMIN) {
            menuCases.add("List All Rentals");
            menuCases.add("Search For a Rental");
        }
        menuCases.add("Exit");
        return menuCases;
    }



	public void createNewRental() {
		// TODO: Implement method
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
