package com.example.Utils;

import java.util.Optional;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;

public final class UserProfilePrinter {
     private UserProfilePrinter() {
    }

   
    public static void printUserDetails(User user) {
        if (user == null) {
            System.out.println("Error: User object is null.");
            return;
        }
        System.out.println("\n--- User Account Details ---");       
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());
    }

    
    public static void printCustomerDetails(Optional<Customer> optionalCustomer) {
        System.out.println("\n--- Customer Profile Details ---");
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();           
            System.out.println("Full Name: " + customer.getCustomerName() + " " + customer.getCustomerSurname());
            System.out.println("Age: " + customer.getAge());
            System.out.println("Company Name: " + customer.getCompanyName());
        } else {
            System.out.println("No customer profile associated with this user account.");
        }
    }

    
    public static void printFullUserProfile(User user, Optional<Customer> optionalCustomer) {
        printUserDetails(user);
        printCustomerDetails(optionalCustomer);
    }

}
