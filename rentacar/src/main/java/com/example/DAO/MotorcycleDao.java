package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.MotorcycleDto;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Utils.Enums.MotorcycleMobility;

@Repository
public interface MotorcycleDao extends JpaRepository<Motorcycle, Integer> {

    @Query("SELECT m FROM Motorcycle m WHERE m.plateOrTailNumber = :identifier")
    Optional<Motorcycle> findByPlateOrTailNumber(@Param("identifier") String identifier);

    String DTO_CONSTRUCTOR_QUERY = "new com.example.DTO.MotorcycleDto(" +
            "m.id, m.brandName, m.modelName, m.modelYear, m.plateOrTailNumber, " +
            "m.currentFuel, m.maxFuelCapacity, m.vehicleValue, m.vehicleStatus, " +
            "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
            "m.km, m.engineCC, m.mobilityType)";

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p")
    List<MotorcycleDto> findAllMotorcyclesAsDto();

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.plateOrTailNumber = :identifier")
    Optional<MotorcycleDto> findByPlateOrTailNumberAsDto(@Param("identifier") String identifier);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.brandName = :brandName")
    List<MotorcycleDto> findByBrandNameAsDto(@Param("brandName") String brandName);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.modelName = :modelName")
    List<MotorcycleDto> findByModelNameAsDto(@Param("modelName") String modelName);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.modelYear = :modelYear")
    List<MotorcycleDto> findByModelYearAsDto(@Param("modelYear") Integer modelYear);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.engineCC > :cc")
    List<MotorcycleDto> findByEngineCCGreaterThanAsDto(@Param("cc") Integer cc);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.mobilityType = :mobilityType")
    List<MotorcycleDto> findByMobilityTypeAsDto(@Param("mobilityType") MotorcycleMobility mobilityType);
}
