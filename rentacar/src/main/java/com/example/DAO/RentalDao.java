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
    List<Rental> findByVehicle_Id(Integer vehicleId);

    List<Rental> findByCustomer_CustomerId(Integer customerId);

  @Query("SELECT new com.example.DTO.RentalHistoryDto(" +
           "r.rentalId, " +
           "v.brandName, " +
           "v.modelName, " +
           "COALESCE(TREAT(v AS Automobile).plate, TREAT(v AS Motorcycle).plate, TREAT(v AS Helicopter).tailNumber), " +
           "r.rentDate, c.actualDropoffDate, c.checkoutAmount, r.rentalStatus) " +
           "FROM Rental r " +
           "JOIN r.vehicle v " +
           "JOIN r.checkout c " +
           "WHERE r.customer.customerId = :customerId " +
           "ORDER BY r.rentDate DESC")
    List<RentalHistoryDto> findRentalHistoryByCustomerId(@Param("customerId") Integer customerId);
}
