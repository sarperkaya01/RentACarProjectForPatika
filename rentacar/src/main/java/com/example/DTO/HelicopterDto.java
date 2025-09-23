package com.example.DTO;

import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.VehicleStatus;
import java.math.BigDecimal;

public class HelicopterDto {

    private Integer id;
    private String brandName;
    private String modelName;
    private Integer modelYear;
    private String tailNumber;
    private BigDecimal flightHours;
    private HeliSpeciality speciality;
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

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public BigDecimal getFlightHours() {
        return flightHours;
    }

    public void setFlightHours(BigDecimal flightHours) {
        this.flightHours = flightHours;
    }

    public HeliSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(HeliSpeciality speciality) {
        this.speciality = speciality;
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