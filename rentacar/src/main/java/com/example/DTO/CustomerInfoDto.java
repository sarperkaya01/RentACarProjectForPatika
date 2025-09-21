package com.example.DTO;

import com.example.Utils.Enums.UserRoles;

public class CustomerInfoDto {

    private final String customerName;
    private final String customerSurname;
    private final Integer age;
    private final String companyName;

    // User sınıfından gelen alanlar
    private final String email;
    private final UserRoles role;

    public CustomerInfoDto(String customerName, String customerSurname, Integer age, String companyName,
            String email, UserRoles role) {
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.age = age;
        this.companyName = companyName;
        this.email = email;
        this.role = role;
    }

    // --- Getter'lar ---

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public Integer getAge() {
        return age;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public UserRoles getRole() {
        return role;
    }
// --- toString() Metodu ---

    @Override
    public String toString() {
        return String.format(
                "----------------------------------------\n" +
                        " Customer        : %s %s\n" +
                        " Age             : %d\n" +
                        " Company         : %s\n" +
                        " Email (UserName) : %s\n" +
                        " Account Role    : %s",
                customerName,
                customerSurname,
                age,
                companyName.equals("-") ? "No company" : companyName,
                email,
                role);
    }
}
