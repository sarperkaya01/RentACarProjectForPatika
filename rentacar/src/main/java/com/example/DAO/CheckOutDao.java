package com.example.DAO;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.Entities.Renting.Checkout;


public interface CheckOutDao extends JpaRepository<Checkout,Integer>{
    Optional<Checkout> findByRental_RentalId(Integer rentalId);
    

}
