package com.example.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.RentalDao;

import com.example.DTO.RentalInfoDto;
import com.example.DTO.RentalListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Entities.Renting.Checkout;
import com.example.Entities.Renting.Rental;

import com.example.Utils.Enums.RentalStatus;
import com.example.Utils.Enums.VehicleStatus;

@Service
public class RentalServices {
    private final RentalDao rentalDao;

    @Autowired
    public RentalServices(RentalDao rentalDao) {
        this.rentalDao = rentalDao;

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

    @Transactional(readOnly = true)
    public List<RentalListDto> getRentalsByRentalStatusAsListDto(RentalStatus status) {
        return rentalDao.findByRentalStatusAsListDto(status);
    }

    @Transactional
    public Rental updateRentalStatus(Integer rentalId, RentalStatus newStatus) {
        Rental rental = getRentalById(rentalId);
        rental.setRentalStatus(newStatus);
        return rentalDao.save(rental);
    }

    @Transactional
    public Rental saveNewRental(Customer customer, Vehicle vehicle, Checkout checkout) {
        Rental newRental = new Rental();
        if (vehicle.getVehicleStatus() != VehicleStatus.AVAILABLE) {
            throw new IllegalStateException(
                    "Vehicle is not available for rent. Current status: " + vehicle.getVehicleStatus());
        }
        newRental.setCustomer(customer);
        newRental.setCheckout(checkout);
        newRental.setVehicle(vehicle);
        newRental.setRentDate(LocalDateTime.now());
        newRental.setRentalStatus(RentalStatus.RENTED);

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
