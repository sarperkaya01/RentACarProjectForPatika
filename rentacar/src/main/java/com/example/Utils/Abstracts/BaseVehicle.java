package com.example.Utils.Abstracts;

import com.example.Entities.DbModels.Vehicles.VehicleProperties;


import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand_name", nullable = false, length = 30)
    private String brandName;

    @Column(name = "model_name", nullable = false, length = 30)
    private String modelName;

    @Column(name = "model_year", nullable = false)
    private Integer modelYear;

    @Column(name = "vehicle_value", nullable = false)
    private Integer vehicleValue;

    @ManyToOne
    @JoinColumn(name = "prop_id", nullable = false)
    private VehicleProperties properties;

    // @Enumerated(EnumType.STRING)
    // @Column(name = "vehicle_status", nullable = false, length = 10)
    // private VehicleStatus vehicleStatus;

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

    public Integer getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(Integer vehicleValue) {
        this.vehicleValue = vehicleValue;
    }
}
