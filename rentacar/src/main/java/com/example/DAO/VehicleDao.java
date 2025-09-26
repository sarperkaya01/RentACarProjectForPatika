package com.example.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.VehicleStatus;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Integer> {

     @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, " +
           "CASE " +
           "  WHEN TYPE(v) = com.example.Entities.DbModels.Vehicles.Automobile THEN 'AUTOMOBILE' " +
           "  WHEN TYPE(v) = com.example.Entities.DbModels.Vehicles.Motorcycle THEN 'MOTORCYCLE' " +
           "  WHEN TYPE(v) = com.example.Entities.DbModels.Vehicles.Helicopter THEN 'HELICOPTER' " +
           "  ELSE 'UNKNOWN' " +
           "END, " +
           "v.brandName, " +
           "v.modelName, " +
           "v.plateOrTailNumber, " +
           "v.vehicleStatus, " +
           "p.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties p")
    List<VehicleListDto> findAllAsVehicleListDto();

    @Query("SELECT v FROM Vehicle v WHERE v.properties.propId = :propId")
    List<Vehicle> findByPropertiesPropId(@Param("propId") Integer propId);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, CASE WHEN TYPE(v) = Automobile THEN 'AUTOMOBILE' WHEN TYPE(v) = Motorcycle THEN 'MOTORCYCLE' WHEN TYPE(v) = Helicopter THEN 'HELICOPTER' ELSE 'UNKNOWN' END, " +
           "v.brandName, v.modelName, v.plateOrTailNumber, v.vehicleStatus, p.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties p WHERE v.modelYear = :modelYear")
    List<VehicleListDto> findByModelYearAsDto(@Param("modelYear") Integer modelYear);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, CASE WHEN TYPE(v) = Automobile THEN 'AUTOMOBILE' WHEN TYPE(v) = Motorcycle THEN 'MOTORCYCLE' WHEN TYPE(v) = Helicopter THEN 'HELICOPTER' ELSE 'UNKNOWN' END, " +
           "v.brandName, v.modelName, v.plateOrTailNumber, v.vehicleStatus, p.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties p WHERE v.modelName = :modelName")
    List<VehicleListDto> findByModelNameAsDto(@Param("modelName") String modelName);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, CASE WHEN TYPE(v) = Automobile THEN 'AUTOMOBILE' WHEN TYPE(v) = Motorcycle THEN 'MOTORCYCLE' WHEN TYPE(v) = Helicopter THEN 'HELICOPTER' ELSE 'UNKNOWN' END, " +
           "v.brandName, v.modelName, v.plateOrTailNumber, v.vehicleStatus, p.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties p WHERE v.brandName = :brandName")
    List<VehicleListDto> findByBrandNameAsDto(@Param("brandName") String brandName);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, CASE WHEN TYPE(v) = Automobile THEN 'AUTOMOBILE' WHEN TYPE(v) = Motorcycle THEN 'MOTORCYCLE' WHEN TYPE(v) = Helicopter THEN 'HELICOPTER' ELSE 'UNKNOWN' END, " +
           "v.brandName, v.modelName, v.plateOrTailNumber, v.vehicleStatus, p.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties p WHERE v.vehicleStatus = :vehicleStatus")
    List<VehicleListDto> findByVehicleStatusAsDto(@Param("vehicleStatus") VehicleStatus vehicleStatus);

    @Query("SELECT new com.example.DTO.VehicleListDto(" +
           "v.id, CASE WHEN TYPE(v) = Automobile THEN 'AUTOMOBILE' WHEN TYPE(v) = Motorcycle THEN 'MOTORCYCLE' WHEN TYPE(v) = Helicopter THEN 'HELICOPTER' ELSE 'UNKNOWN' END, " +
           "v.brandName, v.modelName, v.plateOrTailNumber, v.vehicleStatus, p.dailyPricing) " +
           "FROM Vehicle v JOIN v.properties p WHERE v.vehicleValue BETWEEN :minValue AND :maxValue")
    List<VehicleListDto> findByVehicleValueBetweenAsDto(@Param("minValue") Integer minValue, @Param("maxValue") Integer maxValue);

}
