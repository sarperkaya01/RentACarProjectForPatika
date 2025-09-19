package com.example.Utils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.DTO.RentalHistoryDto;

public class RentalHistoryPrinter {

     public static void printRentalHistory(List<RentalHistoryDto> history, String ownerName) {
        System.out.println("\n--- Rental History for " + ownerName + " ---");

        if (history == null || history.isEmpty()) {
            System.out.println("No rental history found.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (RentalHistoryDto item : history) {
            System.out.println("----------------------------------------");
            System.out.println("Rental ID: " + item.getRentalId());
            System.out.println("Vehicle: " + item.getVehicleInfo());
            System.out.println("Rented On: " + item.getRentDate().format(formatter));
            
            String returnDate = item.getActualDropoffDate() != null ? 
                                item.getActualDropoffDate().format(formatter) : 
                                "Not returned yet";
            System.out.println("Returned On: " + returnDate);
            
            String totalAmount = item.getTotalAmount() != null ? 
                                 String.format("$%.2f", item.getTotalAmount()) :
                                 "In progress";
            System.out.println("Total Amount: " + totalAmount);
            System.out.println("Status: " + item.getRentalStatus());
        }
        System.out.println("----------------------------------------");
    }

}
