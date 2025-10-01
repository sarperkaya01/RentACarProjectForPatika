package com.example.DAO;

import com.example.DTO.AutomobileInfoDto;
import com.example.DTO.VehicleListDto;
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

       String LIST_DTO_CONSTRUCTOR = "new com.example.DTO.VehicleListDto(" +
                     "a.id, a.vehicleType, a.brandName, a.modelName, a.plateOrTailNumber, a.vehicleStatus, p.dailyPricing)";

       String INFO_DTO_CONSTRUCTOR = "new com.example.DTO.AutomobileInfoDto(" +
                     "a.id, a.brandName, a.modelName, a.modelYear, a.plateOrTailNumber, " +
                     "a.currentFuel, a.maxFuelCapacity, a.vehicleValue, a.vehicleStatus, " +
                     "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
                     "a.km, a.wheelDriveType)";

       // --- INFO DTO (DETAY) SORGULARI ---

       @Query("SELECT " + INFO_DTO_CONSTRUCTOR
                     + " FROM Automobile a JOIN a.pricing p WHERE a.plateOrTailNumber = :identifier")
       Optional<AutomobileInfoDto> findByPlateOrTailNumberAsInfoDto(@Param("identifier") String identifier);

       // --- VehicleListDto için ---

     @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                     + " FROM Automobile a JOIN a.pricing p ORDER BY a.brandName, a.modelName")
       List<VehicleListDto> findAllAsListDto();

       @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                     + " FROM Automobile a JOIN a.pricing p WHERE a.brandName = :brandName")
       List<VehicleListDto> findByBrandNameAsListDto(@Param("brandName") String brandName);

       @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                     + " FROM Automobile a JOIN a.pricing p WHERE a.modelName = :modelName")
       List<VehicleListDto> findByModelNameAsListDto(@Param("modelName") String modelName);

       @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                     + " FROM Automobile a JOIN a.pricing p WHERE a.modelYear = :modelYear")
       List<VehicleListDto> findByModelYearAsListDto(@Param("modelYear") Integer modelYear);

       @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                     + " FROM Automobile a JOIN a.pricing p WHERE a.wheelDriveType = :wheelDriveType")
       List<VehicleListDto> findByWheelDriveTypeAsListDto(@Param("wheelDriveType") WheelDriveType wheelDriveType);

}