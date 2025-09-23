package com.example.DTO;

import com.example.Utils.Enums.MotorcycleMobility;
import com.example.Utils.Enums.VehicleStatus;
import java.math.BigDecimal;

public class MotorcycleDto {

    private Integer id;
    private String brandName;
    private String modelName;
    private Integer modelYear;
    private String plate;
    private Integer engineCC;
    private MotorcycleMobility mobilityType;
    private VehicleStatus vehicleStatus;
    private BigDecimal dailyPricing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Integer getEngineCC() {
        return engineCC;
    }

    public void setEngineCC(Integer engineCC) {
        this.engineCC = engineCC;
    }

    public MotorcycleMobility getMobilityType() {
        return mobilityType;
    }

    public void setMobilityType(MotorcycleMobility mobilityType) {
        this.mobilityType = mobilityType;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public BigDecimal getDailyPricing() {
        return dailyPricing;
    }

    public void setDailyPricing(BigDecimal dailyPricing) {
        this.dailyPricing = dailyPricing;
    }
}