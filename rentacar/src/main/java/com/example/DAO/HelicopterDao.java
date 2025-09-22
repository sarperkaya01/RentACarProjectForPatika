package com.example.DAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Utils.Enums.HeliSpeciality;


@Repository
public interface HelicopterDao extends JpaRepository<Helicopter, Integer>{

    Optional<Helicopter> findByTailNumber(String tailNumber);
    List<Helicopter> findBySpeciality(HeliSpeciality speciality);
    List<Helicopter> findByFlightHoursGreaterThan(BigDecimal hours);

}
