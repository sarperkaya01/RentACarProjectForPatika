package com.example.Controllers;

import com.example.Controllers.SelectFactories.CustomerSelectFactory;
import com.example.Controllers.UpdateFactories.CustomerUpdateFactory;
import com.example.DTO.CustomerListDto;
import com.example.Services.CustomerServices;
import com.example.Utils.Global;
import com.example.Utils.Interfaces.Controller;
import com.example.Utils.Interfaces.SummarizableController; // <-- DOĞRU İSİM
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Component
public class UserController implements Controller, SummarizableController<CustomerListDto> {

    private final CustomerServices customerServices;
    private final CustomerSelectFactory customerSelectFactory;
    private final CustomerUpdateFactory customerUpdateFactory;

    public UserController(CustomerServices customerServices,
            CustomerSelectFactory customerSelectFactory,
            CustomerUpdateFactory customerUpdateFactory) {
        this.customerServices = customerServices;
        this.customerSelectFactory = customerSelectFactory;
        this.customerUpdateFactory = customerUpdateFactory;
    }

    @Override
    public void start() {
        runMenuLoop("User Management");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {

        return Arrays.asList(
                "List All Users",
                "Search User by Field",
                "Update User Info",
                "Exit");
    }

     @Override
    public Supplier<List<CustomerListDto>> getSummaryDtoListSupplier() {
        return () -> customerServices.getAllCustomersAsListDto();
    }

    @Override
    public String getEntityName() {
        return "User";
    }

    public void listAllUsers() {
        listAllSummary();
    }

    public void searchUserByField() {
        customerSelectFactory.start();
    }

    public void updateUserInfo() {
        System.out.println("\n--- Update User Information ---");
        listAllSummary();

        System.out.print("\nPlease enter the ID of the customer profile you want to update: ");
        try {
            int customerId = Integer.parseInt(Global.scanner.nextLine());

            customerUpdateFactory.start(customerId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Operation cancelled.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}