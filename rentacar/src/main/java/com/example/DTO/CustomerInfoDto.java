package com.example.DTO;

import com.example.Utils.Enums.UserRoles;

public class CustomerInfoDto {

    private final Integer customerId;
    private final String customerName;
    private final String customerSurname;
    private final Integer age;
    private final String companyName;

    // İlişkili User'dan gelen alanlar
    private final Integer userId;
    private final String email;
    private final UserRoles role;

    public CustomerInfoDto(
            Integer customerId, String customerName, String customerSurname, Integer age, String companyName,
            Integer userId, String email, UserRoles role) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.age = age;
        this.companyName = companyName;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    // Gerekli Getter'lar
    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public String getCustomerSurname() {
        return customerSurname;
    }

    public String getEmail() {
        return email;
    }

    // toString() metodu, tüm bilgileri okunaklı bir formatta sunar.
    @Override
    public String toString() {
        return String.format(
            "----------------------------------------\n" +
            " Customer Profile\n" +
            "----------------------------------------\n" +
            " Customer ID : %d\n" +
            " User ID     : %d\n" +
            " Name        : %s %s\n" +
            " Age         : %d\n" +
            " Company     : %s\n" +
            " Email       : %s\n" +
            " Role        : %s\n" +
            "----------------------------------------",
            customerId,
            userId,
            customerName,
            customerSurname,
            age,
            companyName,
            email,
            role
        );
    }
}
