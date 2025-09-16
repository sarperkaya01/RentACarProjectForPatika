package com.example.DAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entities.Renting.CheckOut;


public interface CheckOutDao extends JpaRepository<CheckOut,Integer>{
    Optional<CheckOut> findByRentalId(Integer rentalId);
    BigDecimal calculateLateFee(LocalDateTime expectedDate, LocalDateTime actualDate, BigDecimal dailyRate);

}
