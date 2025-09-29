package com.example.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.CustomerDao;
import com.example.DAO.RentalDao;
import com.example.DAO.VehicleDao;
import com.example.DTO.RentalInfoDto;
import com.example.DTO.RentalListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.Renting.Checkout;
import com.example.Entities.Renting.Rental;
import com.example.Utils.Enums.CheckoutStatus;
import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleStatus;

@Service
public class RentalServices {
    private final RentalDao rentalDao;
    private final VehicleDao vehicleDao;
    private final CustomerDao customerDao;

    @Autowired
    public RentalServices(RentalDao rentalDao, VehicleDao vehicleDao, CustomerDao customerDao) {
        this.rentalDao = rentalDao;
        this.vehicleDao = vehicleDao;
        this.customerDao = customerDao;

    }

    @Transactional
    public Rental createNewRental(Integer customerId, Integer vehicleId,
            LocalDateTime plannedDropoffDate, BigDecimal plannedPrice, BigDecimal deposit) {

        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer not found with id: " + customerId));

        Vehicle vehicle = vehicleDao.findById(vehicleId)
                .orElseThrow(() -> new IllegalStateException("Vehicle not found with id: " + vehicleId));

        if (vehicle.getVehicleStatus() != VehicleStatus.AVAILABLE) {
            throw new IllegalStateException(
                    "Vehicle is not available for rent. Current status: " + vehicle.getVehicleStatus());
        }

        Checkout newCheckout = new Checkout();
        newCheckout.setPlannedDropoffDate(plannedDropoffDate);
        newCheckout.setPlannedPrice(plannedPrice);
        newCheckout.setDeposit(deposit);
        newCheckout.setCheckoutStatus(CheckoutStatus.IN_PROGRESS);

        Rental newRental = new Rental();
        newRental.setCustomer(customer);
        newRental.setVehicle(vehicle);
        newRental.setRentDate(LocalDateTime.now());
        newRental.setRentalStatus(RentalStatus.RENTED);
        newRental.setCheckout(newCheckout);

        vehicle.setVehicleStatus(VehicleStatus.RENTED);

        return rentalDao.save(newRental);
    }

    public List<Rental> getAllRentals() {
        return rentalDao.findAll();
    }

    @Transactional
    public Rental updateRental(Rental rentalToUpdate) {

        getRentalById(rentalToUpdate.getRentalId());

        return rentalDao.save(rentalToUpdate);
    }

    @Transactional(readOnly = true)
    public List<RentalListDto> getAllRentalsAsListDto() {
        return rentalDao.findAllAsListDto();
    }

    @Transactional(readOnly = true)
    public Optional<RentalInfoDto> getRentalByIdAsInfoDto(Integer id) {
        return rentalDao.findByIdAsInfoDto(id);
    }
    
    @Transactional(readOnly = true)
    public List<RentalListDto> getRentalsByCustomerAsListDto(Customer customer) {
        return rentalDao.findByCustomerAsListDto(customer);
    }

    public Rental getRentalById(Integer id) {
        return rentalDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Rental record not found. ID: " + id));
    }

    // --- CRUD: DELETE ---

    @Transactional
    public void deleteRental(Integer id) {
        Rental rentalToDelete = getRentalById(id);
        
        if (rentalToDelete.getRentalStatus() == RentalStatus.INACTIVE) {
            throw new IllegalStateException("Cannot delete an active rental. It must be completed or canceled first.");
        }
        
        rentalDao.deleteById(id);
    }

}
