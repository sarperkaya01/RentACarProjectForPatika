package com.example.DTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

public class VehicleListDto {
    private final Integer vehicleId;
    private final VehicleTypes type;
    private final String brandName;
    private final String modelName;
    private final String plateOrTailNumber;
    private final VehicleStatus status;
    private final BigDecimal dailyPricing;

    public VehicleListDto(Integer vehicleId, String vehicleType, String brandName, String modelName,
            String plateOrTailNumber, VehicleStatus status, BigDecimal dailyPricing) {
        this.vehicleId = vehicleId;
        this.type = VehicleTypes.valueOf(vehicleType);
        this.brandName = brandName;
        this.modelName = modelName;
        this.plateOrTailNumber = plateOrTailNumber;
        this.status = status;
        this.dailyPricing = dailyPricing;
    }

    public VehicleTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = (dailyPricing != null) ? currencyFormatter.format(dailyPricing) + "/day" : "N/A";

        return String.format(
                "ID: %-4d | Type: %-12s | Brand: %-15s | Model: %-20s | Identifier: %-12s | Status: %-12s | Daily Price: %s",
                this.vehicleId,
                this.type,
                this.brandName,
                this.modelName,
                this.plateOrTailNumber,
                this.status,
                formattedPrice);
    }

}
