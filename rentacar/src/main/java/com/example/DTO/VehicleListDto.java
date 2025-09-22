package com.example.DTO;

import java.math.BigDecimal;

import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

public class VehicleListDto {
     private final Integer vehicleId;
    private final VehicleTypes type;
    private final String brandAndModel;
    private final String identifier; // Plaka veya Kuyruk Numarası
    private final VehicleStatus status;
    private final String dailyPrice;

    // Constructor, artık JPQL'den gelen ham verileri alıp DTO içinde işleyecek.
    public VehicleListDto(Integer vehicleId, String vehicleType, String brandName, String modelName, 
                          String identifier, VehicleStatus status, BigDecimal dailyPrice) {
        this.vehicleId = vehicleId;
        this.type = VehicleTypes.valueOf(vehicleType); // String'i Enum'a çeviriyoruz.
        this.brandAndModel = brandName + " " + modelName; // Marka ve modeli birleştiriyoruz.
        this.identifier = identifier;
        this.status = status;
        this.dailyPrice = String.format("$%.2f/day", dailyPrice); // Fiyatı formatlıyoruz.
    }
    
    // toString() metodun, istediğin formatta BİREBİR AYNI KALACAK.
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
