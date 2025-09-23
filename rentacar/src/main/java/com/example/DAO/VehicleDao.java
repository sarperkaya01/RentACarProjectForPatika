package com.example.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.DTO.AutomobileDto;
import com.example.DTO.HelicopterDto;
import com.example.DTO.MotorcycleDto;
import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.VehicleStatus;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByProperties_PropId(Integer propId);

    List<Vehicle> findByModelYear(Integer modelYear);

    List<Vehicle> findByModelName(String modelName);

    List<Vehicle> findByBrandName(String brandName);

    List<Vehicle> findByVehicleStatus(VehicleStatus vehicleStatus);

    List<Vehicle> findByVehicleValueBetween(Integer minValue, Integer maxValue);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
            "v.id, v.vehicleType, v.brandName, v.modelName, " +
            "COALESCE(TREAT(v AS com.example.Entities.DbModels.Vehicles.Automobile).plate, " +
            "TREAT(v AS com.example.Entities.DbModels.Vehicles.Motorcycle).plate, " +
            "TREAT(v AS com.example.Entities.DbModels.Vehicles.Helicopter).tailNumber), " +
            "v.vehicleStatus, prop.dailyPricing) " +
            "FROM Vehicle v JOIN v.properties prop")
    List<VehicleListDto> findAllAsVehicleListDto();

    @Query("SELECT new com.example.DTO.AutomobileDto(" +
           "a.id, a.brandName, a.modelName, a.modelYear, a.plate, a.km, a.wheelDriveType, a.vehicleStatus, prop.dailyPricing) " +
           "FROM Automobile a JOIN a.properties prop")
    List<AutomobileDto> findAllAutomobilesAsDto();

    @Query("SELECT new com.example.DTO.MotorcycleDto(" +
           "m.id, m.brandName, m.modelName, m.modelYear, m.plate, m.engineCC, m.mobilityType, m.vehicleStatus, prop.dailyPricing) " +
           "FROM Motorcycle m JOIN m.properties prop")
    List<MotorcycleDto> findAllMotorcyclesAsDto();

    @Query("SELECT new com.example.DTO.HelicopterDto(" +
           "h.id, h.brandName, h.modelName, h.modelYear, h.tailNumber, h.flightHours, h.speciality, h.vehicleStatus, prop.dailyPricing) " +
           "FROM Helicopter h JOIN h.properties prop")
    List<HelicopterDto> findAllHelicoptersAsDto();

}
