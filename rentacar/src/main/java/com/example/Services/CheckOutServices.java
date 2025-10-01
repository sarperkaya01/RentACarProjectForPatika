package com.example.Services;

import com.example.DAO.CheckoutDao;
import com.example.DAO.RentalDao;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.DbModels.Vehicles.VehiclePricing;
import com.example.Entities.Renting.Checkout;
import com.example.Entities.Renting.Rental;
import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class CheckoutServices {

    private final CheckoutDao checkoutDao;
    private final RentalDao rentalDao;

    @Autowired
    public CheckoutServices(CheckoutDao checkoutDao, RentalDao rentalDao) {
        this.checkoutDao = checkoutDao;
        this.rentalDao = rentalDao;
    }

    // --- CRUD: READ ---

    @Transactional(readOnly = true)
    public List<Checkout> getAllCheckouts() {
        return checkoutDao.findAll();
    }

    public Checkout getCheckoutById(Integer checkoutId) {
        return checkoutDao.findById(checkoutId)
                .orElseThrow(() -> new IllegalStateException("Checkout not found with id: " + checkoutId));
    }

    public Checkout getCheckoutByRentalId(Integer rentalId) {
        return checkoutDao.findByRental_RentalId(rentalId)
                .orElseThrow(() -> new IllegalStateException("Checkout not found for rental id: " + rentalId));
    }

    // --- CRUD: UPDATE ---

    @Transactional
    public Checkout finalizeRental(Integer rentalId, LocalDateTime actualDropoffDate, BigDecimal repairFee) {
        Rental rental = rentalDao.findById(rentalId)
                .orElseThrow(() -> new IllegalStateException("Rental not found with id: " + rentalId));

        if (rental.getRentalStatus() == RentalStatus.COMPLETED) {
            throw new IllegalStateException("This rental has already been completed.");
        }

        Checkout checkout = rental.getCheckout();
        Vehicle vehicle = rental.getVehicle();

        BigDecimal lateFee = calculateLateFee(checkout.getPlannedDropoffDate(), actualDropoffDate, vehicle.getPricing());
        BigDecimal finalRepairFee = Objects.requireNonNullElse(repairFee, BigDecimal.ZERO);
        BigDecimal totalAmount = checkout.getPlannedPrice()
                .add(lateFee)
                .add(finalRepairFee);

        checkout.setActualDropoffDate(actualDropoffDate);
        checkout.setRepairFee(finalRepairFee);
        checkout.setLateFee(lateFee);
        checkout.setCheckoutAmount(totalAmount);
        checkout.setCheckoutStatus(CheckoutStatus.PAID);

        rental.setRentalStatus(RentalStatus.COMPLETED);
        vehicle.setVehicleStatus(VehicleStatus.AVAILABLE);

        rentalDao.save(rental);

        return checkout;
    }

    private BigDecimal calculateLateFee(LocalDateTime planned, LocalDateTime actual, VehiclePricing vehiclepricing) {
        if (actual != null && planned != null && actual.isAfter(planned)) {
            long lateHours = ChronoUnit.HOURS.between(planned, actual);
            if (lateHours > 0) {
                BigDecimal hourlyRate = vehiclepricing.getDailyPricing().divide(new BigDecimal(24), 2, RoundingMode.HALF_UP);
                return hourlyRate.multiply(new BigDecimal(lateHours));
            }
        }
        return BigDecimal.ZERO;
    }
}