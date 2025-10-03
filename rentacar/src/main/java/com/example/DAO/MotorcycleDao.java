package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.MotorcycleInfoDto;
import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Utils.Enums.MotorcycleMobility;

@Repository
public interface MotorcycleDao extends JpaRepository<Motorcycle, Integer> {

        @Query("SELECT m FROM Motorcycle m WHERE m.plateOrTailNumber = :identifier")
        Optional<Motorcycle> findByPlateOrTailNumber(@Param("identifier") String identifier);

        String LIST_DTO_CONSTRUCTOR = "new com.example.DTO.VehicleListDto(" +
                        "m.id, m.vehicleType, m.brandName, m.modelName, m.plateOrTailNumber, m.vehicleStatus, p.dailyPricing)";

        String INFO_DTO_CONSTRUCTOR = "new com.example.DTO.MotorcycleInfoDto(" +
                        "m.id, m.brandName, m.modelName, m.modelYear, m.plateOrTailNumber, " +
                        "m.vehicleValue, m.vehicleStatus, " +
                        "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
                        "m.km, m.engineCC, m.mobilityType)";

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR
                        + " FROM Motorcycle m JOIN m.pricing p WHERE m.plateOrTailNumber = :identifier")
        Optional<MotorcycleInfoDto> findByPlateOrTailNumberAsInfoDto(@Param("identifier") String identifier);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                        + " FROM Motorcycle m JOIN m.pricing p ORDER BY m.brandName, m.modelName")
        List<VehicleListDto> findAllAsListDto();

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Motorcycle m JOIN m.pricing p WHERE m.brandName = :brandName")
        List<VehicleListDto> findByBrandNameAsListDto(@Param("brandName") String brandName);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Motorcycle m JOIN m.pricing p WHERE m.modelName = :modelName")
        List<VehicleListDto> findByModelNameAsListDto(@Param("modelName") String modelName);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Motorcycle m JOIN m.pricing p WHERE m.modelYear = :modelYear")
        List<VehicleListDto> findByModelYearAsListDto(@Param("modelYear") Integer modelYear);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Motorcycle m JOIN m.pricing p WHERE m.engineCC > :cc")
        List<VehicleListDto> findByEngineCCGreaterThanAsListDto(@Param("cc") Integer cc);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR
                        + " FROM Motorcycle m JOIN m.pricing p WHERE m.mobilityType = :mobilityType")
        List<VehicleListDto> findByMobilityTypeAsListDto(@Param("mobilityType") MotorcycleMobility mobilityType);
}