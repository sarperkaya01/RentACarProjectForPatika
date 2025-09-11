package com.example.Entities.DbModels.Vehicles;

import com.example.Services.VehicleStatus;

public class Vehicle {
    private Integer vehicleId;
    private Integer typeId;
    private String plateCode;
    private Integer modelYear;
    private String modelName;
    private String brandName;
    private Double km;
    private Double fuel;
    private Integer vehicleValue;
    private VehicleStatus vehicleStatus;
    
    
    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId + ", typeId=" + typeId + ", plateCode=" + plateCode + ", modelYear="
                + modelYear + ", modelName=" + modelName + ", brandName=" + brandName + ", km=" + km + ", fuel=" + fuel
                + ", vehicleValue=" + vehicleValue + ", vehicleStatus=" + vehicleStatus + "]";
    }
    public Integer getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public String getPlateCode() {
        return plateCode;
    }
    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }
    public Integer getModelYear() {
        return modelYear;
    }
    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public Double getKm() {
        return km;
    }
    public void setKm(Double km) {
        this.km = km;
    }
    public Double getFuel() {
        return fuel;
    }
    public void setFuel(Double fuel) {
        this.fuel = fuel;
    }
    public Integer getVehicleValue() {
        return vehicleValue;
    }
    public void setVehicleValue(Integer vehicleValue) {
        this.vehicleValue = vehicleValue;
    }
    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }
    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
   
    
}
