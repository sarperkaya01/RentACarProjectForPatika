package com.example.Controllers.InsertFactories;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;

import com.example.Services.RegisterService;
import com.example.Utils.Interfaces.InsertFactory;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CustomerInsertFactory implements InsertFactory<Customer, RegisterService> {

    // private final CustomerServices customerServices;
    private final RegisterService registerService;

    public CustomerInsertFactory(RegisterService registerService) {

        this.registerService = registerService;

    }

    @Override
    public RegisterService getSavingService() {
        return this.registerService;
    }

    @Override
    public String getSaveMethodName(Class<Customer> entityType) {

        return "-";
    }

    @Override
    public List<String> getFieldsToSkip() {
        return Arrays.asList("customerId", "user");
    }

    public void start(User u) {

        Customer r = runDynamicInsertMenu(Customer.class);//

        try {
            // Tek bir metot çağrısıyla tüm işlemi transaction güvencesiyle başlat.
            registerService.registerNewCustomer(u, r);
            System.out.println("Customer successfully registered!");
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            // Burada hata olduğunda, veritabanına hiçbir şey yazılmadığından emin
            // olabilirsin.
        }

    }

}