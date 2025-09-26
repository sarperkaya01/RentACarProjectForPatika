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

       @Query("SELECT a FROM Automobile a WHERE a.plateOrTailNumber = :identifier")
       Optional<Automobile> findByPlateOrTailNumber(@Param("identifier") String identifier);

       // DTO constructor'ının doğru sırasını burada bir kez tanımlıyoruz.
       String DTO_CONSTRUCTOR_QUERY = "new com.example.DTO.AutomobileDto(" +
                     "a.id, a.brandName, a.modelName, a.modelYear, a.plateOrTailNumber, " +
                     "a.currentFuel, a.maxFuelCapacity, a.vehicleValue, a.vehicleStatus, " +
                     "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
                     "a.km, a.wheelDriveType)";

       @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Automobile a JOIN a.properties p")
       List<AutomobileDto> findAllAutomobileAsDto();

       @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                     + " FROM Automobile a JOIN a.properties p WHERE a.plateOrTailNumber = :identifier")
       Optional<AutomobileDto> findByPlateOrTailNumberAsDto(@Param("identifier") String identifier);

       @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                     + " FROM Automobile a JOIN a.properties p WHERE a.brandName = :brandName")
       List<AutomobileDto> findByBrandNameAsDto(@Param("brandName") String brandName);

       @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                     + " FROM Automobile a JOIN a.properties p WHERE a.modelName = :modelName")
       List<AutomobileDto> findByModelNameAsDto(@Param("modelName") String modelName);

       @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                     + " FROM Automobile a JOIN a.properties p WHERE a.modelYear = :modelYear")
       List<AutomobileDto> findByModelYearAsDto(@Param("modelYear") Integer modelYear);

       @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                     + " FROM Automobile a JOIN a.properties p WHERE a.wheelDriveType = :wheelDriveType")
       List<AutomobileDto> findByWheelDriveTypeAsDto(@Param("wheelDriveType") WheelDriveType wheelDriveType);

}