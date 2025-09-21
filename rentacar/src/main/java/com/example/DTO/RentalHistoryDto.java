package com.example.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.Utils.Enums.CheckOutStatus;

public class RentalHistoryDto {
   
    // Rental'dan ve Vehicle'dan gelenler
    private final String vehicleBrand;
    private final String vehicleModel;
    private final String vehiclePlate;
    private final LocalDateTime rentDate;
    
    // CheckOut'tan gelen alanlar
    private final LocalDateTime plannedDropoffDate;
    private final LocalDateTime actualDropoffDate;
    private final BigDecimal plannedPrice;
    private final BigDecimal deposit;
    private final BigDecimal lateFee;
    private final BigDecimal repairFee;
    private final BigDecimal checkoutAmount;
    private final CheckOutStatus checkoutStatus;

    public RentalHistoryDto(String vehicleBrand, String vehicleModel, String vehiclePlate, LocalDateTime rentDate,
                            LocalDateTime plannedDropoffDate, LocalDateTime actualDropoffDate,
                            BigDecimal plannedPrice, BigDecimal deposit, BigDecimal lateFee,
                            BigDecimal repairFee, BigDecimal checkoutAmount, CheckOutStatus checkoutStatus) {
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehiclePlate = vehiclePlate;
        this.rentDate = rentDate;
        this.plannedDropoffDate = plannedDropoffDate;
        this.actualDropoffDate = actualDropoffDate;
        this.plannedPrice = plannedPrice;
        this.deposit = deposit;
        this.lateFee = lateFee;
        this.repairFee = repairFee;
        this.checkoutAmount = checkoutAmount;
        this.checkoutStatus = checkoutStatus;
    }

    // --- Getter'lar ---

    public String getVehicleBrand() { return vehicleBrand; }
    public String getVehicleModel() { return vehicleModel; }
    public String getVehiclePlate() { return vehiclePlate; }
    public LocalDateTime getRentDate() { return rentDate; }
    public LocalDateTime getPlannedDropoffDate() { return plannedDropoffDate; }
    public LocalDateTime getActualDropoffDate() { return actualDropoffDate; }
    public BigDecimal getPlannedPrice() { return plannedPrice; }
    public BigDecimal getDeposit() { return deposit; }
    public BigDecimal getLateFee() { return lateFee; }
    public BigDecimal getRepairFee() { return repairFee; }
    public BigDecimal getCheckoutAmount() { return checkoutAmount; }
    public CheckOutStatus getCheckoutStatus() { return checkoutStatus; }

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
            " Vehicle           : %s %s (%s)\n" +
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
            " Status            : %s",
            vehicleBrand, vehicleModel, vehiclePlate,
            rentDate.format(formatter),
            plannedReturnStr,
            actualReturnStr,
            plannedPriceStr,
            depositStr,
            lateFeeStr,
            repairFeeStr,
            totalAmountStr,
            checkoutStatus
        );
    }
}
