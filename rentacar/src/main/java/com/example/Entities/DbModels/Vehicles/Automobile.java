package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;

import com.example.Utils.Enums.WheelDriveType;

import com.example.Utils.Interfaces.LandVehicle;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.Table;

@Entity
@Table(name = "automobiles")
@DiscriminatorValue("AUTOMOBILE")
public class Automobile extends Vehicle implements LandVehicle {

    

    @Column(name = "km", nullable = false, precision = 12, scale = 2)
    private BigDecimal km;

    @Enumerated(EnumType.STRING)
    @Column(name = "wheel_drive_type", nullable = false, length = 20)
    private WheelDriveType wheelDriveType;

    public BigDecimal getKm() {
        return km;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public WheelDriveType getWheelDriveType() {
        return wheelDriveType;
    }

    public void setWheelDriveType(WheelDriveType wheelDriveType) {
        this.wheelDriveType = wheelDriveType;
    }

    // --- Arayüz Metotlarının Uygulanması ---

    @Override
    public void addKm(BigDecimal distance) {
        this.km = this.km.add(distance);
    }

}
