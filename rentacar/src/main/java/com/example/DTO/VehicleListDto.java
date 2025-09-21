package com.example.DTO;

import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

public class VehicleListDto {
     private final Integer vehicleId; // Ana 'vehicles' tablosundan
    private final Integer specificId; // 'automobiles', 'motorcycles' vb. tablolarından
    private final VehicleTypes type;
    private final String brandAndModel;
    private final String identifier; // Plaka veya Kuyruk Numarası
    private final VehicleStatus status;
    private final String dailyPrice;

    public VehicleListDto(Integer vehicleId, Integer specificId, VehicleTypes type, String brandAndModel, String identifier, 
                          VehicleStatus status, java.math.BigDecimal dailyPrice) {
        this.vehicleId = vehicleId;
        this.specificId = specificId;
        this.type = type;
        this.brandAndModel = brandAndModel;
        this.identifier = identifier;
        this.status = status;
        this.dailyPrice = String.format("$%.2f/day", dailyPrice);
    }
    
    @Override
    public String toString() {
        return String.format(
            "ID: %-4d | Type: %-12s | Name: %-25s | Identifier: %-12s | Status: %-12s | Price: %s",
            vehicleId,
            type,
            brandAndModel,
            identifier,
            status,
            dailyPrice
        );
    }

}
