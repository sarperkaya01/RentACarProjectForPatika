package com.example.DTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.WheelDriveType;

public class AutomobileDto extends VehicleDetailDto {

    private final BigDecimal km;
    private final WheelDriveType wheelDriveType;

    public AutomobileDto(
            Integer vehicleId, String brandName, String modelName, Integer modelYear,
            String plateOrTailNumber, BigDecimal currentFuel, BigDecimal maxFuelCapacity,
            Integer vehicleValue, VehicleStatus vehicleStatus,
            BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing,
            BigDecimal km, WheelDriveType wheelDriveType) {

        super(vehicleId, brandName, modelName, modelYear, plateOrTailNumber,
                currentFuel, maxFuelCapacity, vehicleValue, vehicleStatus,
                dailyPricing, weeklyPricing, monthlyPricing);

        this.km = km;
        this.wheelDriveType = wheelDriveType;
    }

    @Override
    public String getSpecificDetails() {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        numberFormatter.setMaximumFractionDigits(2);

        String kmStr = km != null ? numberFormatter.format(km) + " km" : "N/A";

        return String.format(
                " Odometer          : %s\n" +
                        " Drivetrain        : %s\n",
                kmStr,
                this.wheelDriveType);
    }
}