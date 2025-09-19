package com.example.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalHistoryDto {
    private final Integer rentalId;
    private final String vehicleInfo;
    private final LocalDateTime rentDate;
    private final LocalDateTime actualDropoffDate;
    private final BigDecimal totalAmount;
    private final String rentalStatus;

    public RentalHistoryDto(Integer rentalId, String vehicleBrand, String vehicleModel, String vehiclePlate,
            LocalDateTime rentDate, LocalDateTime actualDropoffDate,
            BigDecimal totalAmount, String rentalStatus) {
        this.rentalId = rentalId;
        this.vehicleInfo = String.format("%s %s (%s)", vehicleBrand, vehicleModel, vehiclePlate);
        this.rentDate = rentDate;
        this.actualDropoffDate = actualDropoffDate;
        this.totalAmount = totalAmount;
        this.rentalStatus = rentalStatus;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public LocalDateTime getActualDropoffDate() {
        return actualDropoffDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }
}
