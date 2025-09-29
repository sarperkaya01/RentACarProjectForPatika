package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.MotorcycleInfoDto;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Utils.Enums.MotorcycleMobility;

@Repository
public interface MotorcycleDao extends JpaRepository<Motorcycle, Integer> {

    @Query("SELECT m FROM Motorcycle m WHERE m.plateOrTailNumber = :identifier")
    Optional<Motorcycle> findByPlateOrTailNumber(@Param("identifier") String identifier);

    // 🔑 DTO Constructor projection --> doğru sınıf: MotorcycleInfoDto
    String DTO_CONSTRUCTOR_QUERY = "new com.example.DTO.MotorcycleInfoDto(" +
            "m.id, m.brandName, m.modelName, m.modelYear, m.plateOrTailNumber, " +
            "m.currentFuel, m.maxFuelCapacity, m.vehicleValue, m.vehicleStatus, " +
            "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
            "m.km, m.engineCC, m.mobilityType)";

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p")
    List<MotorcycleInfoDto> findAllMotorcyclesAsDto();

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.plateOrTailNumber = :identifier")
    Optional<MotorcycleInfoDto> findByPlateOrTailNumberAsDto(@Param("identifier") String identifier);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.brandName = :brandName")
    List<MotorcycleInfoDto> findByBrandNameAsDto(@Param("brandName") String brandName);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.modelName = :modelName")
    List<MotorcycleInfoDto> findByModelNameAsDto(@Param("modelName") String modelName);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.modelYear = :modelYear")
    List<MotorcycleInfoDto> findByModelYearAsDto(@Param("modelYear") Integer modelYear);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.engineCC > :cc")
    List<MotorcycleInfoDto> findByEngineCCGreaterThanAsDto(@Param("cc") Integer cc);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Motorcycle m JOIN m.properties p WHERE m.mobilityType = :mobilityType")
    List<MotorcycleInfoDto> findByMobilityTypeAsDto(@Param("mobilityType") MotorcycleMobility mobilityType);
}