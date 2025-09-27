package com.example.Controllers.UpdateFactories;

import com.example.Entities.DbModels.People.Customer;
import com.example.Services.CustomerServices;

import com.example.Utils.Interfaces.UpdateFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomerUpdateFactory implements UpdateFactory<Customer, CustomerServices> {

    private final CustomerServices customerServices;

    public CustomerUpdateFactory(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    @Override
    public CustomerServices getUpdateService() {

        return this.customerServices;
    }

    public void start(Integer customerId) {
        runUpdateMenu(customerId, Customer.class);
    }

    @Override
    public List<String> getAdditionalUpdatableFields() {

        return Arrays.asList("email", "role");
    }
}