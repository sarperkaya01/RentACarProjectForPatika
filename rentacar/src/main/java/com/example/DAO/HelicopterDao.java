package com.example.DAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.HelicopterInfoDto;
import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Utils.Enums.HeliSpeciality;

@Repository
public interface HelicopterDao extends JpaRepository<Helicopter, Integer> {

        @Query("SELECT h FROM Helicopter h WHERE h.plateOrTailNumber = :identifier")
        Optional<Helicopter> findByPlateOrTailNumber(@Param("identifier") String identifier);

        String LIST_DTO_CONSTRUCTOR = "new com.example.DTO.VehicleListDto(" +
                        "h.id, 'HELICOPTER', h.brandName, h.modelName, h.plateOrTailNumber, h.vehicleStatus, p.dailyPricing)";

        String INFO_DTO_CONSTRUCTOR = "new com.example.DTO.HelicopterInfoDto(" +
                        "h.id, h.brandName, h.modelName, h.modelYear, h.plateOrTailNumber, " +
                        "h.currentFuel, h.maxFuelCapacity, h.vehicleValue, h.vehicleStatus, " +
                        "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
                        "h.flightHours, h.speciality)";

        // --- INFO DTO ---
        @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.plateOrTailNumber = :identifier")
        Optional<HelicopterInfoDto> findByPlateOrTailNumberAsInfoDto(@Param("identifier") String identifier);

        // --- LIST DTO ---
        @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p ORDER BY h.brandName, h.modelName")
        List<VehicleListDto> findAllAsListDto();

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.brandName = :brandName")
        List<VehicleListDto> findByBrandNameAsListDto(@Param("brandName") String brandName);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.modelName = :modelName")
        List<VehicleListDto> findByModelNameAsListDto(@Param("modelName") String modelName);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.modelYear = :modelYear")
        List<VehicleListDto> findByModelYearAsListDto(@Param("modelYear") Integer modelYear);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.speciality = :speciality")
        List<VehicleListDto> findBySpecialityAsListDto(@Param("speciality") HeliSpeciality speciality);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.flightHours > :hours")
        List<HelicopterInfoDto> findByFlightHoursGreaterThanAsInfoDto(@Param("hours") BigDecimal hours);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.brandName = :brandName")
        List<HelicopterInfoDto> findByBrandNameAsInfoDto(@Param("brandName") String brandName);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.modelName = :modelName")
        List<HelicopterInfoDto> findByModelNameAsInfoDto(@Param("modelName") String modelName);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.modelYear = :modelYear")
        List<HelicopterInfoDto> findByModelYearAsInfoDto(@Param("modelYear") Integer modelYear);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR +
                        " FROM Helicopter h JOIN h.properties p WHERE h.speciality = :speciality")
        List<HelicopterInfoDto> findBySpecialityAsInfoDto(@Param("speciality") HeliSpeciality speciality);
}