package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.RentalInfoDto;
import com.example.DTO.RentalListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.Renting.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer> {

    // --- DTO CONSTRUCTOR STRINGS ---

    String LIST_DTO_CONSTRUCTOR = "new com.example.DTO.RentalListDto(" +
            "r.rentalId, c.customerName, c.customerSurname, v.brandName, v.modelName, " +
            "co.checkoutAmount, r.rentDate, r.rentalStatus)";

    String INFO_DTO_CONSTRUCTOR = "new com.example.DTO.RentalInfoDto(" +
            "r.rentalId, r.rentDate, r.rentalStatus, " +
            "c.customerId, c.customerName, c.customerSurname, u.email, c.companyName, " +
            "v.id, v.vehicleType, v.brandName, v.modelName, v.plateOrTailNumber, " +
            "co.checkoutId, co.plannedDropoffDate, co.actualDropoffDate, co.plannedPrice, co.deposit, " +
            "co.lateFee, co.repairFee, co.checkoutAmount, co.checkoutStatus)";

    // --- DTO DÖNDÜREN METOTLAR ---

    @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
            " FROM Rental r " +
            " JOIN r.customer c " +
            " JOIN r.vehicle v " +
            " JOIN r.checkout co " +
            " ORDER BY r.rentDate DESC")
    List<RentalListDto> findAllAsListDto();

    @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
            " FROM Rental r " +
            " JOIN r.customer c " +
            " JOIN c.user u " +
            " JOIN r.vehicle v " +
            " JOIN r.checkout co " +
            " WHERE r.rentalId = :rentalId")
    Optional<RentalInfoDto> findByIdAsInfoDto(@Param("rentalId") Integer rentalId);

    @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
            " FROM Rental r JOIN r.customer c JOIN r.vehicle v JOIN r.checkout co " +
            " WHERE r.customer = :customer ORDER BY r.rentDate DESC")
    List<RentalListDto> findByCustomerAsListDto(@Param("customer") Customer customer);
}
