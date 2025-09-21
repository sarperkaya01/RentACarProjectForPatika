package com.example.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.DbModels.Vehicles.Helicopter;


@Repository
public interface HelicopterDao extends JpaRepository<Helicopter, Integer>{

    Optional<Helicopter> findByTailNumber(String tailNumber);

}
