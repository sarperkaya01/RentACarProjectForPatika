package com.example.DAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.DTO.HelicopterDto;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Utils.Enums.HeliSpeciality;


@Repository
public interface HelicopterDao extends JpaRepository<Helicopter, Integer>{

     @Query("SELECT h FROM Helicopter h WHERE h.plateOrTailNumber = :identifier")
    Optional<Helicopter> findByPlateOrTailNumber(@Param("identifier") String identifier);

    String DTO_CONSTRUCTOR_QUERY = "new com.example.DTO.HelicopterDto(" +
            "h.id, h.brandName, h.modelName, h.modelYear, h.plateOrTailNumber, " +
            "h.currentFuel, h.maxFuelCapacity, h.vehicleValue, h.vehicleStatus, " +
            "p.dailyPricing, p.weeklyPricing, p.monthlyPricing, " +
            "h.flightHours, h.speciality)";

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p")
    List<HelicopterDto> findAllHelicoptersAsDto();

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p WHERE h.plateOrTailNumber = :identifier")
    Optional<HelicopterDto> findByPlateOrTailNumberAsDto(@Param("identifier") String identifier);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p WHERE h.brandName = :brandName")
    List<HelicopterDto> findByBrandNameAsDto(@Param("brandName") String brandName);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p WHERE h.modelName = :modelName")
    List<HelicopterDto> findByModelNameAsDto(@Param("modelName") String modelName);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p WHERE h.modelYear = :modelYear")
    List<HelicopterDto> findByModelYearAsDto(@Param("modelYear") Integer modelYear);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p WHERE h.flightHours > :hours")
    List<HelicopterDto> findByFlightHoursGreaterThanAsDto(@Param("hours") BigDecimal hours);

    @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Helicopter h JOIN h.properties p WHERE h.speciality = :speciality")
    List<HelicopterDto> findBySpecialityAsDto(@Param("speciality") HeliSpeciality speciality);
}
