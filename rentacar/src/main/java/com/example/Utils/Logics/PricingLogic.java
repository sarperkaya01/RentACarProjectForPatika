package com.example.Utils.Logics;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.Entities.DbModels.Vehicles.Vehicle;

public class PricingLogic {

    private PricingLogic() {}

    private static final BigDecimal DEPOSIT_THRESHOLD_VALUE = new BigDecimal("2000000");
    private static final BigDecimal DEPOSIT_PERCENTAGE = new BigDecimal("0.10"); // %10

    public static BigDecimal calculateDepositForVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleValue() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal vehicleValue = new BigDecimal(vehicle.getVehicleValue());

        // Kural: Aracın değeri eşik değerden büyük veya eşitse %10 depozito hesapla.
        if (vehicleValue.compareTo(DEPOSIT_THRESHOLD_VALUE) >= 0) {
            return vehicleValue.multiply(DEPOSIT_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        }

        // Değilse, standart bir depozito (veya sıfır) döndür.
        // Bu değeri de bir sabit olarak tanımlayabiliriz.
        return new BigDecimal("500.00"); // Örnek standart depozito
    }

}
