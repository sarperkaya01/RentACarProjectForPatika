package com.example.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.DbModels.Vehicles.VehicleProperties;




@Repository
public interface VehiclePropertiesDao extends JpaRepository<VehicleProperties, Integer>{

}
