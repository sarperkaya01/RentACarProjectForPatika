package com.example.DAO;




import org.springframework.data.jpa.repository.JpaRepository;


import com.example.Entities.Renting.Checkout;


public interface CheckoutDao extends JpaRepository<Checkout,Integer>{
    //Optional<Checkout> findByRental_RentalId(Integer rentalId);
    

}
