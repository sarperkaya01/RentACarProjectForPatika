package com.example.DTO;

import com.example.Utils.Abstracts.VehicleDetailDto;
import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.VehicleStatus;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class HelicopterDto extends VehicleDetailDto {

    private final String tailNumber;
    private final BigDecimal flightHours;
    private final HeliSpeciality speciality;

    public HelicopterDto(
            // Ortak alanlar (super constructor için)
            Integer vehicleId, String brandName, String modelName, Integer modelYear,
            Integer vehicleValue, VehicleStatus vehicleStatus,
            BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing,
            
            // Helikoptere özgü alanlar
            String tailNumber, BigDecimal flightHours, HeliSpeciality speciality) {
        
        // 1. Adım: Ortak alanları doldurması için üst sınıfın constructor'ını çağır.
        super(vehicleId, brandName, modelName, modelYear, vehicleValue, vehicleStatus,
              dailyPricing, weeklyPricing, monthlyPricing);
        
        // 2. Adım: Sadece bu sınıfa özgü alanları doldur.
        this.tailNumber = tailNumber;
        this.flightHours = flightHours;
        this.speciality = speciality;
    }

    /**
     * Üst sınıftan gelen ve doldurulması ZORUNLU olan soyut metot.
     * Sadece helikoptere özgü detayları formatlar.
     */
    @Override
    public String getSpecificDetails() {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        String flightHoursStr = flightHours != null ? numberFormatter.format(flightHours) + " hours" : "N/A";

        return String.format(
            " Tail Number       : %s\n" +
            " Flight Hours      : %s\n" +
            " Speciality        : %s\n",
            this.tailNumber,
            flightHoursStr,
            this.speciality
        );
    }
}