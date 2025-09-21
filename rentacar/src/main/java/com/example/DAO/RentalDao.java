package com.example.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.RentalHistoryDto;
import com.example.Entities.Renting.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {
    List<Rental> findByVehicle_VehicleId(Integer vehicleId);

    List<Rental> findByCustomer_CustomerId(Integer customerId);

    @Query("SELECT new com.example.DTO.RentalHistoryDto(" +
           "r.rentalId, " +
           "COALESCE(a.brandName, m.brandName, h.brandName), " +
           "COALESCE(a.modelName, m.modelName, h.modelName), " +
           "COALESCE(a.plate, m.plate, h.tailNumber), " +
           "r.rentDate, c.actualDropoffDate, c.checkoutAmount, r.rentalStatus) " +
           "FROM Rental r " +
           "JOIN r.vehicle v " +
           "JOIN r.checkout c " +
           "LEFT JOIN Automobile a ON a.id = v.vehicleId " +
           "LEFT JOIN Motorcycle m ON m.id = v.vehicleId " +
           "LEFT JOIN Helicopter h ON h.id = v.vehicleId " +
           "WHERE r.customer.customerId = :customerId " +
           "ORDER BY r.rentDate DESC")
    List<RentalHistoryDto> findRentalHistoryByCustomerId(@Param("customerId") Integer customerId);

}
