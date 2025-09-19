package com.example.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.CheckOutDao;
import com.example.DAO.CustomerDao;
import com.example.DAO.RentalDao;
import com.example.DAO.VehicleDao;

import com.example.Entities.Renting.CheckOut;
import com.example.Entities.Renting.Rental;
import com.example.Utils.Enums.CheckOutStatus;
import com.example.Utils.Enums.RentalStatus;

@Service
public class RentalServices {
    private final RentalDao rentalDao;
    private final VehicleDao vehicleDao;
    private final CustomerDao customerDao;
    private final CheckOutDao checkOutDao;

    @Autowired
    public RentalServices(RentalDao rentalDao, VehicleDao vehicleDao, CustomerDao customerDao,
            CheckOutDao checkOutDao) {
        this.rentalDao = rentalDao;
        this.vehicleDao = vehicleDao;
        this.customerDao = customerDao;
        this.checkOutDao = checkOutDao; // YENİ BAĞIMLILIĞI ATA
    }

    @Transactional
    public Rental createRental(Rental newRental, LocalDateTime plannedDropoffDate, 
                               BigDecimal plannedPrice, BigDecimal deposit) {
        // DÜZELTME: Nesneler ve onların ID'leri üzerinden kontrol yap.
        if (newRental.getVehicle() == null || !vehicleDao.existsById(newRental.getVehicle().getVehicleId())) {
            throw new IllegalStateException("Kiralama başarısız. Araç bulunamadı.");
        }
        if (newRental.getCustomer() == null || !customerDao.existsById(newRental.getCustomer().getCustomerId())) {
            throw new IllegalStateException("Kiralama başarısız. Müşteri bulunamadı.");
        }

        newRental.setRentalStatus(RentalStatus.RENTED);
        Rental savedRental = rentalDao.save(newRental);

        CheckOut newCheckOut = new CheckOut();
        newCheckOut.setRental(savedRental); // İlişkiyi kur
        newCheckOut.setPlannedDropoffDate(plannedDropoffDate);
        newCheckOut.setPlannedPrice(plannedPrice);
        newCheckOut.setDeposit(deposit);
        newCheckOut.setCheckoutStatus(CheckOutStatus.IN_PROGRESS);

        checkOutDao.save(newCheckOut);

        return savedRental;
    }

    public Rental getRentalById(Integer id) {
        return rentalDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Kiralama kaydı bulunamadı. ID: " + id));
    }

    public List<Rental> getAllRentals() {
        return rentalDao.findAll();
    }

    @Transactional
    public Rental updateRental(Rental rentalToUpdate) {

        getRentalById(rentalToUpdate.getRentalId());

        return rentalDao.save(rentalToUpdate);
    }

    @Transactional
    public void deleteRental(Integer id) {
        if (!rentalDao.existsById(id)) {
            throw new IllegalStateException("Silme başarısız. Kiralama kaydı bulunamadı. ID: " + id);
        }
        rentalDao.deleteById(id);
    }

    public List<Rental> getRentalsByVehicleId(Integer vehicleId) {
        return rentalDao.findByVehicle_VehicleId(vehicleId);
    }

    public List<Rental> getRentalsByCustomerId(Integer customerId) {
        return rentalDao.findByCustomer_CustomerId(customerId);
    }

}
