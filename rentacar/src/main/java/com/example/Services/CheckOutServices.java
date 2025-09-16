package com.example.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.CheckOutDao;
import com.example.DAO.RentalDao;
import com.example.DAO.VehiclePropertiesDao;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.DbModels.Vehicles.VehicleProperties;
import com.example.Entities.Renting.CheckOut;
import com.example.Entities.Renting.Rental;
import com.example.Utils.Enums.CheckOutStatus;
import com.example.Utils.Enums.DamageType;
import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleStatus;

@Service
public class CheckOutServices {

    private final CheckOutDao checkOutDao;
    private final RentalDao rentalDao;
    private final VehicleServices vehicleServices;
    private final VehiclePropertiesDao vehiclePropertiesDao;

    @Autowired
    public CheckOutServices(CheckOutDao checkOutDao, RentalDao rentalDao, VehicleServices vehicleServices,
            VehiclePropertiesDao vehiclePropertiesDao) {
        this.checkOutDao = checkOutDao;
        this.rentalDao = rentalDao;
        this.vehicleServices = vehicleServices;
        this.vehiclePropertiesDao = vehiclePropertiesDao;
    }

    @Transactional
    public CheckOut processCheckOut(Integer rentalId) {

        Rental rental = rentalDao.findById(rentalId)
                .orElseThrow(() -> new IllegalStateException("There is no rentol record for this ID " + rentalId));

        if (rental.getRentalStatus() == RentalStatus.RENTED) {
            throw new IllegalStateException("Bu kiralama işlemi zaten tamamlanmış...");
        }
         if (checkOutDao.findByRentalId(rentalId).isPresent()) {
            throw new IllegalStateException("Bu kiralama için zaten bir iade süreci başlatılmış.");
        }

        Vehicle vehicle = vehicleServices.getVehicleById(rental.getVehicleId());
        VehicleProperties properties = vehiclePropertiesDao.findById(vehicle.getPropId())
                .orElseThrow(() -> new IllegalStateException("Aracın fiyat bilgileri bulunamadı."));

         BigDecimal dailyLateFee = properties.getDailyPricing()
                                       .multiply(new BigDecimal("0.10"))
                                       .setScale(2, java.math.RoundingMode.HALF_UP);
        rentalDao.save(rental);

        CheckOut newCheckOut = new CheckOut();
        newCheckOut.setRentalId(rentalId);
        newCheckOut.setLateFee(dailyLateFee);
        newCheckOut.setCheckoutAmount(rental.getPlannedPrice());
        newCheckOut.setCheckoutStatus(CheckOutStatus.IN_PROGRESS);

        vehicle.setVehicleStatus(VehicleStatus.RENTED);
        vehicleServices.updateVehicle(vehicle);

        return checkOutDao.save(newCheckOut);
    }

    @Transactional
    public CheckOut updateCheckOut(CheckOut checkoutToUpdate) {
        Integer id = checkoutToUpdate.getCheckoutId();
        if (id == null || !checkOutDao.existsById(id)) {
            throw new IllegalStateException("Güncelleme başarısız. İade kaydı bulunamadı. ID: " + id);
        }
        return checkOutDao.save(checkoutToUpdate);
    }

    @Transactional
    public void deleteCheckOut(Integer id) {
        if (!checkOutDao.existsById(id)) {
            throw new IllegalStateException("Silme başarısız. İade kaydı bulunamadı. ID: " + id);
        }
        checkOutDao.deleteById(id);
    }

    private BigDecimal calculateCheckOutPrice(CheckOut ca , Integer rentalId) {
        Rental rental = rentalDao.findById(rentalId)
                .orElseThrow(() -> new IllegalStateException("There is no rentol record for this ID " + rentalId));
        if (ca.getActualDropoffDate().isAfter(rental.getPlannedDropoffDate())) {
            long lateDays = ChronoUnit.DAYS.between(rental.getPlannedDropoffDate().toLocalDate(), ca.getActualDropoffDate().toLocalDate());

            if (lateDays > 0) {
                
                return ca.getLateFee().multiply(new BigDecimal(lateDays));
            }
        }
        return BigDecimal.ZERO;
    }

    

    private BigDecimal calculateRepairFee(DamageType damageType) {

        if (damageType == null) {
            return DamageType.NO_DAMAGE.getFee();
        }

        return damageType.getFee();
    }

    public CheckOut getCheckOutByRentalId(Integer rentalId) {

        return checkOutDao.findByRentalId(rentalId)

                .orElseThrow(() -> new IllegalStateException(
                        "Bu kiralamaya ait iade kaydı bulunamadı. Rental ID: " + rentalId));
    }

}