package com.example.DAO;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entities.Renting.CheckOut;


public interface CheckOutDao extends JpaRepository<CheckOut,Integer>{
    Optional<CheckOut> findByRental_RentalId(Integer rentalId);
    

}
