package com.example.Utils.Abstracts;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.Utils.Enums.VehicleStatus;

public abstract class VehicleDetailDto {
    // Vehicle entity'sinden gelenler
    protected final Integer vehicleId;
    protected final String brandName;
    protected final String modelName;
    protected final Integer modelYear;
    private final String platOrTailNumber;
    private final BigDecimal currentFuel;
    private final BigDecimal maxFuelCapacity;
    protected final Integer vehicleValue;
    protected final VehicleStatus vehicleStatus;

    // VehicleProperties entity'sinden gelenler (bunlar da her araçta var)
    protected final BigDecimal dailyPricing;
    protected final BigDecimal weeklyPricing;
    protected final BigDecimal monthlyPricing;

    /**
     * Alt sınıfların (AutomobileDto gibi), ortak alanları doldurmak için
     * çağırması gereken zorunlu constructor.
     */

    public VehicleDetailDto(Integer vehicleId, String brandName, String modelName, Integer modelYear,
            String platOrTailNumber, BigDecimal currentFuel, BigDecimal maxFuelCapacity, Integer vehicleValue,
            VehicleStatus vehicleStatus, BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing) {
        this.vehicleId = vehicleId;
        this.brandName = brandName;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.platOrTailNumber = platOrTailNumber;
        this.currentFuel = currentFuel;
        this.maxFuelCapacity = maxFuelCapacity;
        this.vehicleValue = vehicleValue;
        this.vehicleStatus = vehicleStatus;
        this.dailyPricing = dailyPricing;
        this.weeklyPricing = weeklyPricing;
        this.monthlyPricing = monthlyPricing;
    }

    /**
     * Bu soyut metot, her bir alt sınıfın (AutomobileDto, MotorcycleDto vb.)
     * SADECE KENDİNE ÖZGÜ detaylarını formatlı bir String olarak döndürmesini
     * zorunlu kılar.
     * 
     * @return Araca özgü formatlanmış detayları içeren bir String.
     */
    public abstract String getSpecificDetails();

    // private final String platOrTailNumber;
    // private final BigDecimal currentFuel;
    // private final BigDecimal maxFuelCapacity;

    @Override
    public String toString() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        String valueStr = vehicleValue != null ? currencyFormatter.format(vehicleValue) : "N/A";
        String dailyPriceStr = dailyPricing != null ? currencyFormatter.format(dailyPricing) : "N/A";
        String weeklyPriceStr = weeklyPricing != null ? currencyFormatter.format(weeklyPricing) : "N/A";
        String monthlyPriceStr = monthlyPricing != null ? currencyFormatter.format(monthlyPricing) : "N/A";
        String fuelLevel = currentFuel + " / " + maxFuelCapacity;

        // getClass().getSimpleName() -> "AutomobileDto" gibi bir isim verir.
        // replace("Dto", "") -> "Automobile" yapar.
        // toUpperCase() -> "AUTOMOBILE" yapar.
        String vehicleTypeHeader = this.getClass().getSimpleName().replace("Dto", "").toUpperCase();

        return String.format(
                "**************************************************\n" +
                        " %s DETAILS - ID: %d\n" +
                        "--------------------------------------------------\n" +
                        " Make & Model      : %s %s (%d)\n" +
                        " Status            : %s\n" +
                        " Market Value      : %s\n" +
                        "--------------------------------------------------\n" +
                        " SPECIFIC DETAILS\n" +
                        "--------------------------------------------------\n" +
                        " Plate             : %s\n" +                        
                        " Fuel Level        : %s\n" +
                        "%s" + // Buraya araca özgü detaylar (plaka, km vb.) gelecek
                        "--------------------------------------------------\n" +
                        " PRICING (per period)\n" +
                        "--------------------------------------------------\n" +
                        " Daily             : %s\n" +
                        " Weekly            : %s\n" +
                        " Monthly           : %s\n" +
                        "--------------------------------------------------",
                vehicleTypeHeader,
                this.vehicleId,
                this.brandName, this.modelName, this.modelYear,
                this.vehicleStatus,
                valueStr,
                platOrTailNumber,
                fuelLevel,
                getSpecificDetails(), // Soyut metodu burada çağırıyoruz
                dailyPriceStr,
                weeklyPriceStr,
                monthlyPriceStr);
    }

}
