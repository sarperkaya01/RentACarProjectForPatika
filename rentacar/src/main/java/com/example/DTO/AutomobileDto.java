package com.example.DTO;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.WheelDriveType;

public class AutomobileDto {
    private final Integer vehicleId;
    private final String brandName;
    private final String modelName;
    private final Integer modelYear;
    private final Integer vehicleValue;
    private final VehicleStatus vehicleStatus;

    // Automobile'dan gelen alanlar
    private final String plate;
    private final BigDecimal km;
    private final BigDecimal currentFuel;
    private final BigDecimal maxFuelCapacity;
    private final WheelDriveType wheelDriveType;

    // VehicleProperties'ten gelen alanlar
    private final BigDecimal dailyPricing;
    private final BigDecimal weeklyPricing;
    private final BigDecimal monthlyPricing;


    public AutomobileDto(Integer vehicleId, String brandName, String modelName, Integer modelYear, Integer vehicleValue,
            VehicleStatus vehicleStatus, String plate, BigDecimal km, BigDecimal currentFuel,
            BigDecimal maxFuelCapacity, WheelDriveType wheelDriveType, BigDecimal dailyPricing,
            BigDecimal weeklyPricing, BigDecimal monthlyPricing) {
        this.vehicleId = vehicleId;
        this.brandName = brandName;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.vehicleValue = vehicleValue;
        this.vehicleStatus = vehicleStatus;
        this.plate = plate;
        this.km = km;
        this.currentFuel = currentFuel;
        this.maxFuelCapacity = maxFuelCapacity;
        this.wheelDriveType = wheelDriveType;
        this.dailyPricing = dailyPricing;
        this.weeklyPricing = weeklyPricing;
        this.monthlyPricing = monthlyPricing;
    }


    @Override
    public String toString() {
        // Sayı ve para formatlaması için yardımcılar
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        numberFormatter.setMaximumFractionDigits(2);

        // Değerleri formatla, null kontrolü yap
        String kmStr = km != null ? numberFormatter.format(km) + " km" : "N/A";
        String valueStr = vehicleValue != null ? currencyFormatter.format(vehicleValue) : "N/A";
        String fuelStr = (currentFuel != null && maxFuelCapacity != null)
                ? String.format("%.1f / %.1f L (%.0f%%)", currentFuel, maxFuelCapacity,
                        (currentFuel.doubleValue() / maxFuelCapacity.doubleValue() * 100))
                : "N/A";
        String dailyPriceStr = dailyPricing != null ? currencyFormatter.format(dailyPricing) : "N/A";
        String weeklyPriceStr = weeklyPricing != null ? currencyFormatter.format(weeklyPricing) : "N/A";
        String monthlyPriceStr = monthlyPricing != null ? currencyFormatter.format(monthlyPricing) : "N/A";

        // String.format ile şablonu doldur
        return String.format(
                "--------------------------------------------------\n" +
                        " AUTOMOBILE DETAILS - ID: %d\n" +
                        "--------------------------------------------------\n" +
                        " Make & Model      : %s %s (%d)\n" +
                        " Plate             : %s\n" +
                        " Status            : %s\n" +
                        " Market Value      : %s\n" +
                        "--------------------------------------------------\n" +
                        " TECHNICAL SPECIFICATIONS\n" +
                        "--------------------------------------------------\n" +
                        " Odometer          : %s\n" +
                        " Fuel Level        : %s\n" +
                        " Drivetrain        : %s\n" +
                        "--------------------------------------------------\n" +
                        " PRICING (per period)\n" +
                        "--------------------------------------------------\n" +
                        " Daily             : %s\n" +
                        " Weekly            : %s\n" +
                        " Monthly           : %s\n" +
                        "--------------------------------------------------",
                this.vehicleId,
                this.brandName, this.modelName, this.modelYear,
                this.plate,
                this.vehicleStatus,
                valueStr,
                kmStr,
                fuelStr,
                this.wheelDriveType,
                dailyPriceStr,
                weeklyPriceStr,
                monthlyPriceStr);
    }
}