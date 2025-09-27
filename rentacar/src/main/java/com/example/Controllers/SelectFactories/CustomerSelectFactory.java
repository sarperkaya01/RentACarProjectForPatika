package com.example.Controllers.SelectFactories;

import com.example.DTO.CustomerListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Services.CustomerServices;
import com.example.Utils.Interfaces.SelectFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerSelectFactory implements SelectFactory<Customer, CustomerListDto, CustomerServices> {

    private final CustomerServices customerServices;

    public CustomerSelectFactory(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    @Override
    public CustomerServices getSelectService() {
        return this.customerServices;
    }

    public void start() {
        runSelectMenu(Customer.class);
    }
}