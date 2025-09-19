package com.example.Controllers;


import java.util.Arrays;

import java.util.List;
import java.util.Optional;


import com.example.Entities.DbModels.People.Customer;

import com.example.Services.CustomerServices;
import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.UserProfilePrinter;
import com.example.Utils.Interfaces.Controller;

public class ProfileController implements Controller {
    private final UserServices userServices;
    private final CustomerServices customerServices;

    public ProfileController(UserServices userServices, CustomerServices customerServices) {
        this.userServices = userServices;
        this.customerServices = customerServices;
    }

    @Override
    public void start() {
        runMenuLoop("Profile");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Info", "Change Password", "Rental History", "Exit");
    }

    @SuppressWarnings("unused")
    private void info() {

        Optional<Customer> optionalCustomer = customerServices.getCustomerByUserId(Global.currentUser.getUserId());

        UserProfilePrinter.printFullUserProfile(Global.currentUser, optionalCustomer);

        System.out.println("\nPress Enter to return to the profile menu...");
        Global.scanner.nextLine();
    }

    private void changePassword() {
        System.out.print("Please enter your current password to continue: ");
        String currentPassword = Global.scanner.nextLine();

        byte[] currentPasswordHash = HashUtil.sha256(currentPassword);
        if (!Arrays.equals(currentPasswordHash, Global.currentUser.getPasswd())) {
            System.out.println("Incorrect password! Password change cancelled.");
            return;
        }

        String newPassword;
        String newPasswordConfirm;
        do {
            System.out.print("Enter your new password: ");
            newPassword = Global.scanner.nextLine();
            System.out.print("Enter your new password again: ");
            newPasswordConfirm = Global.scanner.nextLine();

            if (!newPassword.equals(newPasswordConfirm)) {
                System.out.println("Passwords do not match. Please try again.");
            }
        } while (!newPassword.equals(newPasswordConfirm));

        byte[] newPasswordHash = HashUtil.sha256(newPassword);
        Global.currentUser.setPasswd(newPasswordHash);
        try {
            userServices.updateUser(Global.currentUser);
            System.out.println("Password has been changed successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the password: " + e.getMessage());

        }

        System.out.println("\nPress Enter to return to the profile menu...");
        Global.scanner.nextLine();
    }

  
}
