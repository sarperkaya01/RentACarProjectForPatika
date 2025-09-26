package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;

import com.example.Utils.Enums.VehicleStatus;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.JOINED) // En önemli kısım burası!
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // Performans için LAZY kullanmak genellikle daha iyidir.
    @JoinColumn(name = "prop_id", nullable = false)
    private VehicleProperties properties;

    @Column(name = "brand_name", nullable = false, length = 30)
    private String brandName;

    @Column(name = "model_name", nullable = false, length = 30)
    private String modelName;

    @Column(name = "model_year", nullable = false)
    private Integer modelYear;

    @Column(name = "plate_or_tailnumber", nullable = false)
    private String plateOrTailNumber;

    @Column(name = "current_fuel", nullable = false, precision = 6, scale = 2)
    private BigDecimal currentFuel;

    @Column(name = "max_fuel_capacity", nullable = false, precision = 6, scale = 2)
    private BigDecimal maxFuelCapacity;

    @Column(name = "vehicle_value", nullable = false)
    private Integer vehicleValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status", nullable = false, length = 20)
    private VehicleStatus vehicleStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleProperties getProperties() {
        return properties;
    }

    public void setProperties(VehicleProperties properties) {
        this.properties = properties;
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

    public String getPlateOrTailNumber() {
        return plateOrTailNumber;
    }

    public void setPlateOrTailNumber(String plateOrTailNumber) {
        this.plateOrTailNumber = plateOrTailNumber;
    }

    public BigDecimal getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(BigDecimal currentFuel) {
        this.currentFuel = currentFuel;
    }

    public BigDecimal getMaxFuelCapacity() {
        return maxFuelCapacity;
    }

    public void setMaxFuelCapacity(BigDecimal maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
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
