package com.example.DAO;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.VehicleStatus;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByProperties_PropId(Integer propId);

    List<Vehicle> findByModelYear(Integer modelYear);

    List<Vehicle> findByModelName(String modelName);

    List<Vehicle> findByBrandName(String brandName);

    List<Vehicle> findByVehicleStatus(VehicleStatus vehicleStatus);

    List<Vehicle> findByVehicleValueBetween(Integer minValue, Integer maxValue);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, " +
           "v.vehicleType, " + 
           "v.brandName, " +
           "v.modelName, " +
           "COALESCE(TREAT(v AS Automobile).plate, TREAT(v AS Motorcycle).plate, TREAT(v AS Helicopter).tailNumber), " +
           "v.vehicleStatus, " +
           "prop.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties prop")
    List<VehicleListDto> findAllAsVehicleListDto();

}
