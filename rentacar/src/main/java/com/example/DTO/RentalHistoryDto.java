package com.example.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalStatus;

public class RentalHistoryDto {
   // Rental
    private final Integer rentalId;

    // Customer
    private final String customerFullName;
    private final String customerCompany;
    
    // Vehicle
    private final String vehicleBrandAndModel;
    private final String vehicleIdentifier; // Plaka veya Kuyruk No

    // Checkout
    private final LocalDateTime rentDate;
    private final LocalDateTime plannedDropoffDate;
    private final LocalDateTime actualDropoffDate;
    private final BigDecimal plannedPrice;
    private final BigDecimal deposit;
    private final BigDecimal lateFee;
    private final BigDecimal repairFee;
    private final BigDecimal checkoutAmount;
    private final RentalStatus rentalStatus;
    private final CheckoutStatus checkoutStatus;

    public RentalHistoryDto(Integer rentalId, String customerName, String customerSurname, String companyName,
                            String vehicleBrand, String vehicleModel, String vehicleIdentifier,
                            LocalDateTime rentDate, LocalDateTime plannedDropoffDate, LocalDateTime actualDropoffDate,
                            BigDecimal plannedPrice, BigDecimal deposit, BigDecimal lateFee,
                            BigDecimal repairFee, BigDecimal checkoutAmount, RentalStatus rentalStatus, CheckoutStatus checkoutStatus) {
        this.rentalId = rentalId;
        this.customerFullName = customerName + " " + customerSurname;
        this.customerCompany = companyName;
        this.vehicleBrandAndModel = vehicleBrand + " " + vehicleModel;
        this.vehicleIdentifier = vehicleIdentifier;
        this.rentDate = rentDate;
        this.plannedDropoffDate = plannedDropoffDate;
        this.actualDropoffDate = actualDropoffDate;
        this.plannedPrice = plannedPrice;
        this.deposit = deposit;
        this.lateFee = lateFee;
        this.repairFee = repairFee;
        this.checkoutAmount = checkoutAmount;
        this.rentalStatus = rentalStatus;
        this.checkoutStatus = checkoutStatus;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String plannedReturnStr = plannedDropoffDate != null ? plannedDropoffDate.format(formatter) : "N/A";
        String actualReturnStr = actualDropoffDate != null ? actualDropoffDate.format(formatter) : "Not returned yet";
        String plannedPriceStr = plannedPrice != null ? String.format("$%.2f", plannedPrice) : "N/A";
        String depositStr = deposit != null ? String.format("$%.2f", deposit) : "N/A";
        String lateFeeStr = lateFee != null ? String.format("$%.2f", lateFee) : "N/A";
        String repairFeeStr = repairFee != null ? String.format("$%.2f", repairFee) : "N/A";
        String totalAmountStr = checkoutAmount != null ? String.format("$%.2f", checkoutAmount) : "In progress";

        return String.format(
                "----------------------------------------\n" +
                " RENTAL INVOICE - ID: %d\n" +
                " Customer          : %s (%s)\n" +
                " ----------------------------------------\n" +
                " Vehicle           : %s (%s)\n" +
                " Rented On         : %s\n" +
                " Planned Return    : %s\n" +
                " Actual Return     : %s\n" +
                " \n" +
                " Planned Price     : %s\n" +
                " Deposit           : %s\n" +
                " Late Fee          : %s\n" +
                " Repair Fee        : %s\n" +
                " ------------------\n" +
                " Total Amount      : %s\n" +
                " \n" +
                " Rental Status            : %s\n" +
                " Chekcout Status.         : %s\\n" +
                "----------------------------------------",
            this.rentalId,
            this.customerFullName, this.customerCompany,
            this.vehicleBrandAndModel, this.vehicleIdentifier,
            this.rentDate.format(formatter),
            plannedReturnStr,
            actualReturnStr,
            plannedPriceStr,
            depositStr,
            lateFeeStr,
            repairFeeStr,
            totalAmountStr,
            this.rentalStatus,
            this.checkoutStatus
        );
    }
}
