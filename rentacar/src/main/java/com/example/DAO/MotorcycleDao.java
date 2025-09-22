package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Utils.Enums.MotorcycleMobility;

@Repository
public interface MotorcycleDao extends JpaRepository<Motorcycle, Integer> {

    Optional<Motorcycle> findByPlate(String plate);
    List<Motorcycle> findByEngineCCGreaterThan(Integer cc);

    List<Motorcycle> findByMobilityType(MotorcycleMobility mobilityType);

}
