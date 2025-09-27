package com.example.DTO;

import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.VehicleStatus;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class HelicopterDto extends VehicleDetailDto {

    private final BigDecimal flightHours;
    private final HeliSpeciality speciality;

    public HelicopterDto(
            Integer vehicleId, String brandName, String modelName, Integer modelYear,
            String plateOrTailNumber, BigDecimal currentFuel, BigDecimal maxFuelCapacity,
            Integer vehicleValue, VehicleStatus vehicleStatus,
            BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing,
            BigDecimal flightHours, HeliSpeciality speciality) {

        super(vehicleId, brandName, modelName, modelYear, plateOrTailNumber,
                currentFuel, maxFuelCapacity, vehicleValue, vehicleStatus,
                dailyPricing, weeklyPricing, monthlyPricing);

        this.flightHours = flightHours;
        this.speciality = speciality;
    }

    @Override
    public String getSpecificDetails() {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        String flightHoursStr = flightHours != null ? numberFormatter.format(flightHours) + " hours" : "N/A";

        return String.format(
                " Flight Hours      : %s\n" +
                " Speciality        : %s\n",
                flightHoursStr,
                this.speciality);
    }
}