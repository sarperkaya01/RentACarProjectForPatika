package com.example.DTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.Utils.Abstracts.VehicleDetailDto;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.WheelDriveType;

public class AutomobileDto extends VehicleDetailDto{
   
    private final String plate;
    private final BigDecimal km;
    private final BigDecimal currentFuel;
    private final BigDecimal maxFuelCapacity;
    private final WheelDriveType wheelDriveType;


   


    public AutomobileDto(Integer vehicleId, String brandName, String modelName, Integer modelYear, Integer vehicleValue,
            VehicleStatus vehicleStatus, BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing,
            String plate, BigDecimal km, BigDecimal currentFuel, BigDecimal maxFuelCapacity,
            WheelDriveType wheelDriveType) {
        super(vehicleId, brandName, modelName, modelYear, vehicleValue, vehicleStatus, dailyPricing, weeklyPricing,
                monthlyPricing);
        this.plate = plate;
        this.km = km;
        this.currentFuel = currentFuel;
        this.maxFuelCapacity = maxFuelCapacity;
        this.wheelDriveType = wheelDriveType;
    }





    @Override
    public String getSpecificDetails() {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        numberFormatter.setMaximumFractionDigits(2);

        String kmStr = km != null ? numberFormatter.format(km) + " km" : "N/A";
        String fuelStr = (currentFuel != null && maxFuelCapacity != null && maxFuelCapacity.compareTo(BigDecimal.ZERO) > 0)
                ? String.format("%.1f / %.1f L (%.0f%%)", currentFuel, maxFuelCapacity,
                (currentFuel.doubleValue() / maxFuelCapacity.doubleValue() * 100))
                : "N/A";

        return String.format(
            " Plate             : %s\n" +
            " Odometer          : %s\n" +
            " Fuel Level        : %s\n" +
            " Drivetrain        : %s\n",
            this.plate,
            kmStr,
            fuelStr,
            this.wheelDriveType
        );
    }
}