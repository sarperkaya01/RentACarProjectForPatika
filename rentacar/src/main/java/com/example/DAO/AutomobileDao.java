package com.example.DAO;

import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Utils.Enums.WheelDriveType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomobileDao extends JpaRepository<Automobile, Integer> {

    Optional<Automobile> findByPlate(String plate);

    List<Automobile> findByWheelDriveType(WheelDriveType wheelDriveType);

}