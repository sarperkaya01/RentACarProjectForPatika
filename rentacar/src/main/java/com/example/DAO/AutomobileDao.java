package com.example.DAO;

import com.example.DTO.AutomobileDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Utils.Enums.WheelDriveType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomobileDao extends JpaRepository<Automobile, Integer> {

    Optional<Automobile> findByPlate(String plate);


    @Query("SELECT new com.example.DTO.AutomobileDto(" +
           "a.id, a.brandName, a.modelName, a.modelYear, a.vehicleValue, a.vehicleStatus, " +
           "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
           "a.plate, a.km, a.currentFuel, a.maxFuelCapacity, a.wheelDriveType) " +
           "FROM Automobile a JOIN a.properties p WHERE a.plate = :plate")
    Optional<AutomobileDto> findByPlateAsDto(@Param("plate") String plate);

    @Query("SELECT new com.example.DTO.AutomobileDto(" +
           "a.id, a.brandName, a.modelName, a.modelYear, a.vehicleValue, a.vehicleStatus, " +
           "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
           "a.plate, a.km, a.currentFuel, a.maxFuelCapacity, a.wheelDriveType) " +
           "FROM Automobile a JOIN a.properties p")
    List<AutomobileDto> findAllAutomobileAsDto();

    @Query("SELECT a FROM Automobile a WHERE a.brandName = :brandName")
    List<Automobile> findByBrandName(@Param("brandName") String brandName);
    
    @Query("SELECT a FROM Automobile a WHERE a.modelName = :modelName")
    List<Automobile> findByModelName(@Param("modelName") String modelName);
    
    @Query("SELECT a FROM Automobile a WHERE a.modelYear = :modelYear")
    List<Automobile> findByModelYear(@Param("modelYear") Integer modelYear);

    @Query("SELECT a FROM Automobile a WHERE a.wheelDriveType = :wheelDriveType")
    List<Automobile> findByWheelDriveType(@Param("wheelDriveType") WheelDriveType wheelDriveType);

}