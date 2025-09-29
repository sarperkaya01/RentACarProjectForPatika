package com.example.DTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.example.Utils.Enums.RentalStatus;

public class RentalListDto {
     private final Integer rentalId;
    private final String customerFullName;
    private final String vehicleIdentifier;
    private final BigDecimal checkoutAmount;
    private final LocalDateTime rentDate;
    private final RentalStatus rentalStatus;

    public RentalListDto(Integer rentalId, String customerName, String customerSurname, String vehicleBrand, String vehicleModel, BigDecimal checkoutAmount, LocalDateTime rentDate, RentalStatus rentalStatus) {
        this.rentalId = rentalId;
        this.customerFullName = customerName + " " + customerSurname;
        this.vehicleIdentifier = vehicleBrand + " " + vehicleModel;
        this.checkoutAmount = checkoutAmount;
        this.rentDate = rentDate;
        this.rentalStatus = rentalStatus;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        String formattedDate = rentDate != null ? rentDate.format(formatter) : "N/A";
        String formattedAmount = checkoutAmount != null ? currencyFormatter.format(checkoutAmount) : "N/A";

        return String.format(
                "ID: %-5d | Customer: %-25s | Vehicle: %-30s | Rent Date: %-16s | Status: %-10s | Amount: %s",
                rentalId,
                customerFullName,
                vehicleIdentifier,
                formattedDate,
                rentalStatus,
                formattedAmount
        );
    }

}
