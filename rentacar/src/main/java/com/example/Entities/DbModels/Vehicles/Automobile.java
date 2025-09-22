package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;



import com.example.Utils.Enums.WheelDriveType;
import com.example.Utils.Interfaces.FuelDriven;
import com.example.Utils.Interfaces.LandVehicle;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "automobiles")
@DiscriminatorValue("AUTOMOBILE") // Discriminator sütununa yazılacak değer
@PrimaryKeyJoinColumn(name = "auto_id")
public class Automobile extends Vehicle implements LandVehicle, FuelDriven {

    @Column(name = "plate", nullable = false, unique = true, length = 15)
    private String plate;

    @Column(name = "km", nullable = false, precision = 12, scale = 2)
    private BigDecimal km;

    @Column(name = "current_fuel", nullable = false, precision = 6, scale = 2)
    private BigDecimal currentFuel;

    @Column(name = "max_fuel_capacity", nullable = false, precision = 6, scale = 2)
    private BigDecimal maxFuelCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "wheel_drive_type", nullable = false, length = 20)
    private WheelDriveType wheelDriveType;

    // --- Arayüz Metotlarının Uygulanması ---

    @Override
    public String getPlate() {
        return this.plate;
    }

    @Override
    public BigDecimal getKm() {
        return this.km;
    }

    @Override
    public void addKm(BigDecimal distance) {
        this.km = this.km.add(distance);
    }

    @Override
    public BigDecimal getCurrentFuel() {
        return this.currentFuel;
    }

    @Override
    public BigDecimal getMaxFuelCapacity() {
        return this.maxFuelCapacity;
    }

    @Override
    public void refuel(BigDecimal amount) {
        this.currentFuel = this.currentFuel.add(amount);
    }

    // --- Getter'lar ve Setter'lar ---

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public void setCurrentFuel(BigDecimal currentFuel) {
        this.currentFuel = currentFuel;
    }

    public void setMaxFuelCapacity(BigDecimal maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }

    public WheelDriveType getWheelDriveType() {
        return wheelDriveType;
    }

    public void setWheelDriveType(WheelDriveType wheelDriveType) {
        this.wheelDriveType = wheelDriveType;
    }

}
