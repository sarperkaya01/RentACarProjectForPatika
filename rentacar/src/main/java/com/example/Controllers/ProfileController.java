package com.example.Controllers;

import com.example.DTO.CustomerInfoDto;

import com.example.Services.CustomerServices;
import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.Interfaces.Controller;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ProfileController implements Controller {
    private final UserServices userServices;
    private final CustomerServices customerServices;

    public ProfileController(UserServices userServices, CustomerServices customerServices) {
        this.userServices = userServices;
        this.customerServices = customerServices;
    }

    @Override
    public void start() {

        runMenuLoop("Profile Menu for " + Global.currentUser.getEmail());
    }

    @Override
    public void exit() {

        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Show My Info", "Change Password", "Add Money", "Exit");
    }

    @SuppressWarnings("unused")
    private void showMyInfo() {

        Optional<CustomerInfoDto> customerDtoOpt = customerServices
                .getCustomersByUserEmailAsInfoDto(Global.currentUser.getEmail());

        if (customerDtoOpt.isPresent()) {
            System.out.println(customerDtoOpt.get());
        } else {

            System.out.println("Could not find profile information for user: " + Global.currentUser.getEmail());
        }
        pause();
    }

    @SuppressWarnings("unused")
    private void changePassword() {
        System.out.print("Please enter your current password to continue: ");
        String currentPassword = Global.scanner.nextLine();

        if (!Arrays.equals(HashUtil.sha256(currentPassword), Global.currentUser.getPasswd())) {
            System.out.println("Incorrect password! Password change cancelled.");
            pause();

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

        try {

            Global.currentUser = userServices.updatePasswd(Global.currentUser.getUserId(), newPassword);

            System.out.println("Password has been changed successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the password: " + e.getMessage());
        }
        pause();
    }

    private void pause() {
        System.out.println("\nPress Enter to return to the profile menu...");
        Global.scanner.nextLine();
    }

    public void addMoney() {

        // boolean k = true;

        // while (k) {

        // System.out.println("Please enter the amount to add to the budget :");
        // if (Global.scanner.hasNextInt()) {

        // Integer input = Global.scanner.nextInt();
        // Global.scanner.nextLine();
        // customerServices.updateBudget(customerServices.get)
        // if (rentalInfoOptional.isPresent()) {
        // System.out.println(rentalInfoOptional.get()); // .get() ile içindeki DTO'yu
        // alıp yazdır
        // } else {
        // System.out.println("Rental with ID " + input + " could not be found.");
        // }

        // k = false;

        // } else {
        // System.out.println("Only insert a digit !");
        // Global.scanner.nextLine();
        // }

        // }
    }
}