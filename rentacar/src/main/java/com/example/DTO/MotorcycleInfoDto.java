package com.example.DTO;

import com.example.Utils.Enums.MotorcycleMobility;
import com.example.Utils.Enums.VehicleStatus;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MotorcycleInfoDto  extends VehicleDetailDto {

  private final BigDecimal km;
    private final Integer engineCC;
    private final MotorcycleMobility mobilityType;

    public MotorcycleInfoDto(
            Integer vehicleId, String brandName, String modelName, Integer modelYear,
            String plateOrTailNumber, BigDecimal currentFuel, BigDecimal maxFuelCapacity,
            Integer vehicleValue, VehicleStatus vehicleStatus,
            BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing,
            BigDecimal km, Integer engineCC, MotorcycleMobility mobilityType) {

        super(vehicleId, brandName, modelName, modelYear, plateOrTailNumber,
              currentFuel, maxFuelCapacity, vehicleValue, vehicleStatus,
              dailyPricing, weeklyPricing, monthlyPricing);

        this.km = km;
        this.engineCC = engineCC;
        this.mobilityType = mobilityType;
    }

    @Override
    public String getSpecificDetails() {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        numberFormatter.setMaximumFractionDigits(2);

        String kmStr = km != null ? numberFormatter.format(km) + " km" : "N/A";
        String engineStr = engineCC != null ? engineCC + " cc" : "N/A";
        String mobilityStr = mobilityType != null ? mobilityType.toString() : "N/A";

        return String.format(
                " Odometer          : %s%n" +
                " Engine Capacity   : %s%n" +
                " Mobility Type     : %s%n",
                kmStr,
                engineStr,
                mobilityStr
        );
    }
}