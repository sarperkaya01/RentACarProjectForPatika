package com.example.Controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Services.CustomerServices;
import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;

@Component
public class RegisterController implements Controller {
    private final UserServices userServices;
    private final MainController mainController;
    private final CustomerServices customerServices;

    @Autowired
    public RegisterController(UserServices userServices, CustomerServices customerServices,
            @Lazy MainController mainController) {
        this.userServices = userServices;
        this.mainController = mainController;
        this.customerServices = customerServices;
    }

    @Override
    public void start() {

        runMenuLoop("Register");

    }

    @Override
    public void exit() {
        mainController.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("New User", "Exit");
    }

    @SuppressWarnings("unused")
    private void newUser() {
        try {
            System.out.println("Please enter your email adress:");
            String email = Global.scanner.nextLine();

            String password1;

            String password2;
            do {

                System.out.println("Write your password:");
                password1 = Global.scanner.nextLine();
                System.out.println("Write your password again:");
                password2 = Global.scanner.nextLine();
                if (!password1.equals(password2)) {
                    System.out.println("Passwords do not match. Please try again.");
                }

            } while (password1 == password2);

            User newUser = new User();
            newUser.setEmail(email);

            newUser.setPasswd(HashUtil.sha256(password1));

            Customer newCustomer = newCustomer(newUser);

            if (newCustomer.getCompanyName() == "-") {
                newUser.setRole(UserRoles.INDIVIDUAL);
            } else {
                newUser.setRole(UserRoles.CORPORATE);
            }

            User savedUser = userServices.newUser(newUser);

            newCustomer.setUser(savedUser);

            customerServices.newCustomer(newCustomer);

            System.out.println("New user added as  " + savedUser.getEmail());

        } catch (IllegalStateException e) {

            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {

            System.out.println("Beklenmedik bir hata oluştu: " + e.getMessage());
        } finally {

            exit();
        }

    }

    private Customer newCustomer(User u) {
        Customer c = new Customer();
        c.setCustomerId(null);
        c.setUser(u);

        System.out.println("Please enter your Name:");
        String customerName = Global.scanner.nextLine();
        c.setCustomerName(customerName);

        System.out.println("Please enter your Surname:");
        String customerSurname = Global.scanner.nextLine();
        c.setCustomerSurname(customerSurname);

        int age = 0;
        while (true) {
            System.out.print("Please enter your Age: ");
            String ageInput = Global.scanner.nextLine();
            try {
                age = Integer.parseInt(ageInput);
                break;
            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a valid number for age.");
            }
        }
        c.setAge(age);        

        System.out.println("Please enter your CompanyName (If you are individual please pass this with 'Enter'):");
        String companyName = Global.scanner.nextLine();

        if (companyName.isEmpty()) {
            companyName = "-";
        }

        c.setCompanyName(companyName);

        return c;
    }

}
