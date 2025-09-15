package com.example.DAO;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.Entities.DbModels.Vehicles.Vehicle;


public interface VehicleDao extends JpaRepository<Vehicle, Integer> {
    Optional<Vehicle> findByEmail(String email);

}
