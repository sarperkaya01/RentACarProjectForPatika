package com.example.DTO;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.Utils.Enums.RentalStatus;

public class RentalListDto {
    private final Integer rentalId;

    public Integer getRentalId() {
        return rentalId;
    }

    private final String customerName;
    private final String customerSurname;
    private final String brandName;
    private final String modelName;
    private final BigDecimal checkoutAmount;
    private final LocalDateTime rentDate;
    private final RentalStatus rentalStatus;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public RentalListDto(Integer rentalId, String customerName, String customerSurname,
            String brandName, String modelName, BigDecimal checkoutAmount,
            LocalDateTime rentDate, RentalStatus rentalStatus) {
        this.rentalId = rentalId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.brandName = brandName;
        this.modelName = modelName;
        this.checkoutAmount = checkoutAmount;
        this.rentDate = rentDate;
        this.rentalStatus = rentalStatus;
    }

    @Override
    public String toString() {
        String customerFullName = this.customerName + " " + this.customerSurname;
        String vehicleDescription = this.brandName + " " + this.modelName;
        String amountDisplay = (this.checkoutAmount != null) ? String.format("%.2f", this.checkoutAmount) : "N/A";

        return String.format(
                "ID: %-6d | Customer: %-25s | Vehicle: %-25s | Rent Date: %-18s | Status: %-12s | Amount: %s",
                this.rentalId,
                customerFullName,
                vehicleDescription,
                this.rentDate.format(formatter),
                this.rentalStatus,
                amountDisplay);
    }

}
