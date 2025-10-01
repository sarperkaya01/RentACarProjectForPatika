package com.example.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.DbModels.Vehicles.VehiclePricing;




@Repository
public interface VehiclePricingDao extends JpaRepository<VehiclePricing, Integer>{
    
    Optional<VehiclePricing> findByVehicleSpeciality(String vehicleSpeciality);

}
