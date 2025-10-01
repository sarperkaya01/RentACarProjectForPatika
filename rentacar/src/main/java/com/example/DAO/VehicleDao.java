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

        String VEHICLE_TYPE_CASE_QUERY = "CASE " +
                        "WHEN TYPE(v) = com.example.Entities.DbModels.Vehicles.Automobile THEN 'AUTOMOBILE' " +
                        "WHEN TYPE(v) = com.example.Entities.DbModels.Vehicles.Motorcycle THEN 'MOTORCYCLE' " +
                        "WHEN TYPE(v) = com.example.Entities.DbModels.Vehicles.Helicopter THEN 'HELICOPTER' " +
                        "END";

        // DTO constructor'ına giden parametre sırası güncellendi.
        String DTO_CONSTRUCTOR_QUERY = "new com.example.DTO.VehicleListDto(" +
                        "v.id, " +
                        VEHICLE_TYPE_CASE_QUERY + ", " +
                        "v.brandName, " +
                        "v.modelName, " +
                        "v.plateOrTailNumber, " +
                        "v.vehicleStatus, " +
                        "p.dailyPricing)";

        @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Vehicle v JOIN v.pricing p")
        List<VehicleListDto> findAllAsVehicleListDto();

      

        @Query("SELECT v FROM Vehicle v WHERE v.pricing.priceId = :priceId")
        List<Vehicle> findBypricingPriceId(@Param("priceId") Integer priceId);

        @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Vehicle v JOIN v.pricing p WHERE v.modelYear = :modelYear")
        List<VehicleListDto> findByModelYearAsDto(@Param("modelYear") Integer modelYear);

        @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Vehicle v JOIN v.pricing p WHERE v.modelName = :modelName")
        List<VehicleListDto> findByModelNameAsDto(@Param("modelName") String modelName);

        @Query("SELECT " + DTO_CONSTRUCTOR_QUERY + " FROM Vehicle v JOIN v.pricing p WHERE v.brandName = :brandName")
        List<VehicleListDto> findByBrandNameAsDto(@Param("brandName") String brandName);

        @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                        + " FROM Vehicle v JOIN v.pricing p WHERE v.vehicleStatus = :vehicleStatus")
        List<VehicleListDto> findByVehicleStatusAsDto(@Param("vehicleStatus") VehicleStatus vehicleStatus);

        @Query("SELECT " + DTO_CONSTRUCTOR_QUERY
                        + " FROM Vehicle v JOIN v.pricing p WHERE v.vehicleValue BETWEEN :minValue AND :maxValue")
        List<VehicleListDto> findByVehicleValueBetweenAsDto(@Param("minValue") Integer minValue,
                        @Param("maxValue") Integer maxValue);

}
