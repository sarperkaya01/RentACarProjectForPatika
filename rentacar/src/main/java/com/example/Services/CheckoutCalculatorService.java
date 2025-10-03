package com.example.Services;

import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.DbModels.Vehicles.VehiclePricing;
import com.example.Entities.Renting.Checkout;
import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalPeriod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class CheckoutCalculatorService {

    private static final int HOURS_IN_A_WORK_DAY = 10; // Saatlik ücreti hesaplamak için bölen

    /**
     * Verilen kiralama bilgilerine göre bir Checkout nesnesi hazırlar.
     * Bu metot, planlanan fiyatı, planlanan teslim tarihini ve depozitoyu hesaplar.
     *
     * @param vehicle  Kiralanacak olan araç nesnesi (içinde pricing bilgisi
     *                 olmalı).
     * @param period   Kullanıcının seçtiği kiralama periyodu (HOURLY, DAILY, vb.).
     * @param duration Kullanıcının girdiği kiralama süresi (örn: 5 gün, 2 ay).
     * @return Hesaplamaları yapılmış, dolu bir Checkout nesnesi.
     */
    public Checkout prepareCheckout(Vehicle vehicle, RentalPeriod period, int duration) {
        if (vehicle == null || vehicle.getPricing() == null) {
            throw new IllegalArgumentException("Vehicle and its pricing information must not be null.");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be a positive number.");
        }

        VehiclePricing pricing = vehicle.getPricing();
        LocalDateTime rentDate = LocalDateTime.now();

        // 1. Planlanan Fiyatı Hesapla
        BigDecimal plannedPrice = calculatePlannedPrice(pricing, period, duration);

        // 2. Planlanan Teslim Tarihini Hesapla
        LocalDateTime plannedDropoffDate = calculatePlannedDropoffDate(rentDate, period, duration);

        // 3. Depozitoyu Hesapla (Önceki konuşmamızdaki mantığa göre)
        BigDecimal deposit = calculateDeposit(vehicle);

        // 4. Hazırlanan Checkout nesnesini oluştur ve döndür
        Checkout preparedCheckout = new Checkout();
        preparedCheckout.setPlannedPrice(plannedPrice);
        preparedCheckout.setPlannedDropoffDate(plannedDropoffDate);
        preparedCheckout.setDeposit(deposit);
        preparedCheckout.setCheckoutStatus(CheckoutStatus.IN_PROGRESS);

        return preparedCheckout;
    }

    private BigDecimal calculatePlannedPrice(VehiclePricing pricing, RentalPeriod period, int duration) {
        BigDecimal durationBD = BigDecimal.valueOf(duration);
        BigDecimal pricePerUnit;

        switch (period) {
            case HOURLY:
                // Saatlik ücret, günlük ücretin 10'da biri olarak hesaplanır (2 ondalık basamak
                // hassasiyetle)
                pricePerUnit = pricing.getDailyPricing().divide(
                        new BigDecimal(HOURS_IN_A_WORK_DAY), 2, RoundingMode.HALF_UP);
                break;
            case DAILY:
                pricePerUnit = pricing.getDailyPricing();
                break;
            case WEEKLY:
                pricePerUnit = pricing.getWeeklyPricing();
                break;
            case MONTHLY:
                pricePerUnit = pricing.getMonthlyPricing();
                break;
            default:
                throw new IllegalArgumentException("Unsupported rental period: " + period);
        }

        return pricePerUnit.multiply(durationBD);
    }

    private LocalDateTime calculatePlannedDropoffDate(LocalDateTime startDate, RentalPeriod period, int duration) {
        switch (period) {
            case HOURLY:
                return startDate.plusHours(duration);
            case DAILY:
                return startDate.plusDays(duration);
            case WEEKLY:
                return startDate.plusWeeks(duration);
            case MONTHLY:
                return startDate.plusMonths(duration);
            default:
                throw new IllegalArgumentException("Unsupported rental period: " + period);
        }
    }

    private BigDecimal calculateDeposit(Vehicle vehicle) {
        // 2 milyondan değerli araçlar için %10 depozito, diğerleri için sabit 500 TL
        // (örnek kural)
        if (vehicle.getVehicleValue() > 2000000) {
            return BigDecimal.valueOf(vehicle.getVehicleValue()).multiply(new BigDecimal("0.1"));
        } else {
            return new BigDecimal("500.00");
        }
    }
}
