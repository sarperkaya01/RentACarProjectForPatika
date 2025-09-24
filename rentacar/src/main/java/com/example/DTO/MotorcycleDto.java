package com.example.DTO;

import com.example.Utils.Abstracts.VehicleDetailDto;
import com.example.Utils.Enums.MotorcycleMobility;
import com.example.Utils.Enums.VehicleStatus;
import java.math.BigDecimal;

public class MotorcycleDto  extends VehicleDetailDto {

    // --- SADECE MOTOSİKLETE ÖZGÜ ALANLAR ---
    private final String plate;
    private final Integer engineCC;
    private final MotorcycleMobility mobilityType;

    public MotorcycleDto(
            // Ortak alanlar (super constructor için)
            Integer vehicleId, String brandName, String modelName, Integer modelYear,
            Integer vehicleValue, VehicleStatus vehicleStatus,
            BigDecimal dailyPricing, BigDecimal weeklyPricing, BigDecimal monthlyPricing,
            
            // Motosiklete özgü alanlar
            String plate, Integer engineCC, MotorcycleMobility mobilityType) {
        
        // 1. Adım: Ortak alanları doldurması için üst sınıfın constructor'ını çağır.
        super(vehicleId, brandName, modelName, modelYear, vehicleValue, vehicleStatus,
              dailyPricing, weeklyPricing, monthlyPricing);
        
        // 2. Adım: Sadece bu sınıfa özgü alanları doldur.
        this.plate = plate;
        this.engineCC = engineCC;
        this.mobilityType = mobilityType;
    }

    /**
     * Üst sınıftan gelen ve doldurulması ZORUNLU olan soyut metot.
     * Sadece motosiklete özgü detayları formatlar.
     */
    @Override
    public String getSpecificDetails() {
        return String.format(
            " Plate             : %s\n" +
            " Engine Capacity   : %d cc\n" +
            " Mobility Type     : %s\n",
            this.plate,
            this.engineCC,
            this.mobilityType
        );
    }
}