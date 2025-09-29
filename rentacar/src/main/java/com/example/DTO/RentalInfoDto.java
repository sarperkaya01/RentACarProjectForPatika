package com.example.DTO;

import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleTypes;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalInfoDto {
    // Rental
    private final Integer rentalId;
    private final LocalDateTime rentDate;
    private final RentalStatus rentalStatus;

    // Customer
    private final Integer customerId;
    private final String customerName;
    private final String customerSurname;
    private final String customerEmail;
    private final String companyName;

    // Vehicle
    private final Integer vehicleId;
    private final VehicleTypes vehicleType;
    private final String brandName;
    private final String modelName;
    private final String plateOrTailNumber;

    // Checkout
    private final Integer checkoutId;
    private final LocalDateTime plannedDropoffDate;
    private final LocalDateTime actualDropoffDate;
    private final BigDecimal plannedPrice;
    private final BigDecimal deposit;
    private final BigDecimal lateFee;
    private final BigDecimal repairFee;
    private final BigDecimal checkoutAmount;
    private final CheckoutStatus checkoutStatus;

    public RentalInfoDto(Integer rentalId, LocalDateTime rentDate, RentalStatus rentalStatus, Integer customerId,
            String customerName, String customerSurname, String customerEmail, String companyName, Integer vehicleId,
            String vehicleType, String brandName, String modelName, String plateOrTailNumber, Integer checkoutId,
            LocalDateTime plannedDropoffDate, LocalDateTime actualDropoffDate, BigDecimal plannedPrice,
            BigDecimal deposit, BigDecimal lateFee, BigDecimal repairFee, BigDecimal checkoutAmount,
            CheckoutStatus checkoutStatus) {
        this.rentalId = rentalId;
        this.rentDate = rentDate;
        this.rentalStatus = rentalStatus;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerEmail = customerEmail;
        this.companyName = companyName;
        this.vehicleId = vehicleId;
        this.vehicleType = VehicleTypes.valueOf(vehicleType);
        this.brandName = brandName;
        this.modelName = modelName;
        this.plateOrTailNumber = plateOrTailNumber;
        this.checkoutId = checkoutId;
        this.plannedDropoffDate = plannedDropoffDate;
        this.actualDropoffDate = actualDropoffDate;
        this.plannedPrice = plannedPrice;
        this.deposit = deposit;
        this.lateFee = lateFee;
        this.repairFee = repairFee;
        this.checkoutAmount = checkoutAmount;
        this.checkoutStatus = checkoutStatus;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Değerlerin null olma ihtimaline karşı güvenli formatlama
        String rentDateStr = (rentDate != null) ? rentDate.format(formatter) : "N/A";
        String plannedReturnStr = (plannedDropoffDate != null) ? plannedDropoffDate.format(formatter) : "N/A";
        String actualReturnStr = (actualDropoffDate != null) ? actualDropoffDate.format(formatter) : "N/A";
        String plannedPriceStr = (plannedPrice != null) ? currencyFormatter.format(plannedPrice) : "N/A";
        String depositStr = (deposit != null) ? currencyFormatter.format(deposit) : "N/A";
        String lateFeeStr = (lateFee != null) ? currencyFormatter.format(lateFee) : "N/A";
        String repairFeeStr = (repairFee != null) ? currencyFormatter.format(repairFee) : "N/A";
        String totalAmountStr = (checkoutAmount != null) ? currencyFormatter.format(checkoutAmount) : "N/A";

        return String.format(
                "================ RENTAL DETAILS ================\n" +
                        " Rental ID         : %d\n" +
                        " Rental Status     : %s\n" +
                        "------------------ CUSTOMER ------------------\n" +
                        " Customer ID       : %d\n" +
                        " Customer Name     : %s %s\n" +
                        " Company           : %s\n" +
                        " Email             : %s\n" +
                        "------------------- VEHICLE ------------------\n" +
                        " Vehicle ID        : %d\n" +
                        " Vehicle Type      : %s\n" +
                        " Make & Model      : %s %s\n" +
                        " Identifier        : %s\n" +
                        "------------------ CHECKOUT ------------------\n" +
                        " Checkout ID       : %d\n" +
                        " Checkout Status   : %s\n" +
                        " Rent Date         : %s\n" +
                        " Planned Return    : %s\n" +
                        " Actual Return     : %s\n" +
                        " Planned Price     : %s\n" +
                        " Deposit           : %s\n" +
                        " Late Fee          : %s\n" +
                        " Repair Fee        : %s\n" +
                        " TOTAL AMOUNT      : %s\n" +
                        "==============================================",
                // Rental
                rentalId, rentalStatus,
                // Customer
                customerId, customerName, customerSurname, companyName, customerEmail,
                // Vehicle
                vehicleId, vehicleType, brandName, modelName, plateOrTailNumber,
                // Checkout
                checkoutId, checkoutStatus, rentDateStr, plannedReturnStr, actualReturnStr,
                plannedPriceStr, depositStr, lateFeeStr, repairFeeStr, totalAmountStr);
    }
}
