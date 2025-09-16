package com.example.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.Renting.Rental;
@Repository
public interface RentalDao extends JpaRepository<Rental,Integer> {
    List<Rental> findByVehicleId(Integer vehicleId);
    List<Rental> findByCustomerId(Integer customerId);


}
