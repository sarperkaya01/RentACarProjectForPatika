package com.example.Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.Controllers.SelectFactories.RentalSelectFactory;
import com.example.DTO.RentalListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.Renting.Checkout;

import com.example.Services.CheckoutServices;
import com.example.Services.CustomerServices;
import com.example.Services.RentalServices;
import com.example.Services.VehicleService;
import com.example.Utils.Global;
import com.example.Utils.Enums.DamageType;
import com.example.Utils.Enums.RentalPeriod;
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
    private final RentalSelectFactory rentalSelectFactory;

    public RentalsController(RentalServices rentalService, VehicleOperationsController vehicleOpertionsController,
            VehicleService vehicleService, CustomerServices customerService, CheckoutServices checkoutServices,
            RentalSelectFactory rentalSelectFactory) {
        this.rentalService = rentalService;
        this.vehicleOpertionsController = vehicleOpertionsController;
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.checkoutServices = checkoutServices;
        this.rentalSelectFactory = rentalSelectFactory;
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
        } else {
            menuCases.add("My Rental History");
        }
        menuCases.add("Exit");
        return menuCases;
    }

    public void rent() {
        System.out.println("\n--- Start a New Rental ---");
        System.out.print("Enter Vehicle ID (or press Enter to browse vehicles): ");

        String userInput = Global.scanner.nextLine();
        Vehicle vehicleToRent;

        if (userInput.isBlank()) {
            System.out.println("\nNo ID entered. Opening vehicle selection menu...");
            vehicleOpertionsController.start();
            return;
        } else {
            try {
                int vehicleId = Integer.parseInt(userInput);
                vehicleToRent = vehicleService.getVehicleById(vehicleId);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        System.out.println("\nStarting checkout process for: " + vehicleToRent.getBrandName() + " "
                + vehicleToRent.getModelName());

        Checkout createdCheckout = checkoutServices.saveNewCheckout(vehicleToRent);

        if (createdCheckout != null) {
            try {
                Customer customer = customerService.getCustomerById(Global.currentUser.getUserId());
                if (customer == null) {
                    throw new IllegalStateException("Could not find customer details for the logged-in user.");
                }

                rentalService.saveNewRental(customer, vehicleToRent, createdCheckout);

                System.out.println("Rental successfully created! Checkout ID: " + createdCheckout.getCheckoutId());

            } catch (Exception e) {
                System.out.println("\n--- CRITICAL ERROR ---");
                System.out.println("Checkout record was created, but the final rental could not be saved.");
                System.out.println("Please contact support with Checkout ID: " + createdCheckout.getCheckoutId());
                System.out.println("Error details: " + e.getMessage());
            }
        } else {
            System.out.println("\nRental process was canceled or failed.");
        }
    }

    public void finalizeARental() {
        System.out.println("Rental ID (Please press write 'info' and press enter button if you don't):");

        Integer input = null;
        String userInput = Global.scanner.nextLine();

        if (userInput.equals("info")) {
            myRentalHistory();
            return;
        } else {
            input = Integer.parseInt(userInput);
        }
        checkoutServices.finalizeRental(input, distanceTraveled(), repairFee());

    }

    public void listAllRentals() {
        listAllSummary();
    }

    public void searchForARental() {
        rentalSelectFactory.start();
    }

    @Override
    public Supplier<List<RentalListDto>> getSummaryDtoListSupplier() {
        return () -> rentalService.getAllRentalsAsListDto();
    }

    @Override
    public String getEntityName() {
        return "Rental";
    }

    public void myRentalHistory() {
        Customer c = customerService.getCustomerById(Global.currentUser.getUserId());
        rentalService.getRentalsByCustomerAsListDto(c);
    }

    public BigDecimal repairFee() {
        System.out.println("Select rental period:");
        System.out.println("1. NO_DAMAGE");
        System.out.println("2. MINOR_SCRATCH");
        System.out.println("3. DENT");
        System.out.println("4. WINDSHIELD_CRACK");
        System.out.println("5. TIRE_REPLACEMENT");
        System.out.print("Your choice: ");
        int repairChoice = Integer.parseInt(Global.scanner.nextLine());

        boolean k = true;
        while (k) {
            switch (repairChoice) {
                case 1:
                    return DamageType.NO_DAMAGE.getFee();

                case 2:
                    return DamageType.MINOR_SCRATCH.getFee();

                case 3:
                    return DamageType.DENT.getFee();

                case 4:
                    return DamageType.WINDSHIELD_CRACK.getFee();

                case 5:
                    return DamageType.TIRE_REPLACEMENT.getFee();

                default:
                    System.out.println("Invalid choice.");
                    break;

            }

        }

        return DamageType.WINDSHIELD_CRACK.getFee();

    }

    public BigDecimal distanceTraveled() {
        System.out.println("Please enter the distance traveled or flight duration:");

        BigDecimal value = null;
        boolean validInput = false;

        while (!validInput) {
            String input = Global.scanner.nextLine().trim();

            try {
                // Kullanıcı sadece sayı (integer ya da decimal) girmişse burada yakalanır
                value = new BigDecimal(input);

                if (value.compareTo(BigDecimal.ZERO) >= 0) {
                    validInput = true; // pozitif veya sıfır değer kabul edilir
                } else {
                    System.out.println("Value cannot be negative. Please try again:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer or decimal number:");
            }
        }

        return value;
    }
}
