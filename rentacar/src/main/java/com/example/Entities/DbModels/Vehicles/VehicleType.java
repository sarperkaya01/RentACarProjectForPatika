package com.example.Entities.DbModels.Vehicles;

import com.example.Services.VehicleTypes;

public class VehicleType {
    private Integer typeId;
    private VehicleTypes vehicleType;
    private Double hourlyPricing;
    private Double dailyPricing;
    private Double weaklyPricing;
    private Double monthlyPricing;
    @Override
    public String toString() {
        return "VehicleType [typeId=" + typeId + ", vehicleType=" + vehicleType + ", hourlyPricing=" + hourlyPricing
                + ", dailyPricing=" + dailyPricing + ", weaklyPricing=" + weaklyPricing + ", monthlyPricing="
                + monthlyPricing + "]";
    }
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public VehicleTypes getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleTypes vehicleType) {
        this.vehicleType = vehicleType;
    }
    public Double getHourlyPricing() {
        return hourlyPricing;
    }
    public void setHourlyPricing(Double hourlyPricing) {
        this.hourlyPricing = hourlyPricing;
    }
    public Double getDailyPricing() {
        return dailyPricing;
    }
    public void setDailyPricing(Double dailyPricing) {
        this.dailyPricing = dailyPricing;
    }
    public Double getWeaklyPricing() {
        return weaklyPricing;
    }
    public void setWeaklyPricing(Double weaklyPricing) {
        this.weaklyPricing = weaklyPricing;
    }
    public Double getMonthlyPricing() {
        return monthlyPricing;
    }
    public void setMonthlyPricing(Double monthlyPricing) {
        this.monthlyPricing = monthlyPricing;
    }
}
