package com.example.DAO;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.VehicleTypes;



@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Integer> {
    
     Optional<Vehicle> findByDetailTableTypeAndDetailTableId(VehicleTypes detailTableType, Integer detailTableId);
    // Optional<Vehicle> findByPlate(String plate);
    // List<Vehicle> findByProperties_PropId(Integer propId);

    // List<Vehicle> findByModelYear(Integer modelYear);
    // List<Vehicle> findByModelName(String modelName);
    // List<Vehicle> findByBrandName(String brandName);
    // List<Vehicle> findByVehicleStatus(VehicleStatus vehicleStatus);
    // List<Vehicle> findByVehicleValueBetween(Integer minValue, Integer maxValue);

}
