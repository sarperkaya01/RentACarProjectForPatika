package com.example.Services;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Utils.Enums.UserRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final UserServices userServices;
    private final CustomerServices customerServices;

    @Autowired
    public RegisterService(UserServices userServices, CustomerServices customerServices) {
        this.userServices = userServices;
        this.customerServices = customerServices;
    }

    @Transactional
    public Customer registerNewCustomer(User user, Customer customer) {

        String companyName = customer.getCompanyName();
        String finalCompanyName = (companyName == null || companyName.isBlank()) ? "-" : companyName;
        customer.setCompanyName(finalCompanyName);
        UserRoles role = "-".equals(finalCompanyName) ? UserRoles.INDIVIDUAL : UserRoles.CORPORATE;
        user.setRole(role);
        
        User savedUser = userServices.saveNewUser(user);

        customer.setUser(savedUser);

        return customerServices.saveNewCustomer(customer);
    }

}
