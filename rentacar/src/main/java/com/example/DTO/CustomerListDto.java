package com.example.DTO;

import com.example.Utils.Enums.UserRoles;

public class CustomerListDto {

    private final Integer customerId;
    private final String name;
    private final String surName;
    private final String companyName;
    private final String email;
    private final UserRoles role;

    public CustomerListDto(Integer customerId, String name, String surName, String companyName, String email,
            UserRoles role) {
        this.customerId = customerId;
        this.name = name;
        this.surName = surName;
        this.companyName = companyName;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-4d | Name: %-15s| Surname: %-15s | Company: %-20s | Email: %-30s | Role: %s",
                this.customerId,
                this.name,
                this.surName,
                this.companyName,
                this.email,
                this.role);
    }

}
