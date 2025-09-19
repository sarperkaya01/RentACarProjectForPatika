package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;

import com.example.Utils.Enums.VehicleStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @ManyToOne
    @JoinColumn(name = "prop_id", nullable = false)
    private VehicleProperties properties;

    @Column(name = "plate", nullable = false, unique = true, length = 10)
    private String plate;

    @Column(name = "model_year", nullable = false)
    private Integer modelYear;

    @Column(name = "model_name", nullable = false, length = 20)
    private String modelName;

    @Column(name = "brand_name", nullable = false, length = 20)
    private String brandName;

    @Column(name = "km", nullable = false, precision = 12, scale = 2)
    private BigDecimal km;

    @Column(name = "fuel", nullable = false, precision = 5, scale = 2)
    private BigDecimal fuel;

    @Column(name = "vehicle_value", nullable = false)
    private Integer vehicleValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status", nullable = false, length = 10)
    private VehicleStatus vehicleStatus;

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

   public VehicleProperties getProperties() {
        return properties;
    }

    public void setProperties(VehicleProperties properties) {
        this.properties = properties;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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

    public BigDecimal getKm() {
        return km;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public BigDecimal getFuel() {
        return fuel;
    }

    public void setFuel(BigDecimal fuel) {
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

    public Vehicle() {
    }

}
