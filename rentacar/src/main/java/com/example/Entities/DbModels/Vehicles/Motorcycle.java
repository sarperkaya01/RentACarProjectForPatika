package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;


import com.example.Utils.Enums.MotorcycleMobility;

import com.example.Utils.Interfaces.LandVehicle;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "motorcycles")
@DiscriminatorValue("MOTORCYCLE") // vehicles tablosundaki vehicle_type sütununa bu değeri yazacak
@PrimaryKeyJoinColumn(name = "motor_id")
public class Motorcycle extends Vehicle implements LandVehicle {

  
    @Column(name = "km", nullable = false, precision = 12, scale = 2)
    private BigDecimal km;
   
    @Column(name = "engine_cc", nullable = false)
    private Integer engineCC;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobility_type", nullable = false, length = 20)
    private MotorcycleMobility mobilityType;

   
    
    
    // --- Arayüz Metotlarının Uygulanması ---

   
   
    public BigDecimal getKm() {
        return km;
    }
    public void setKm(BigDecimal km) {
        this.km = km;
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
    @Override
    public void addKm(BigDecimal distance) {
        this.km = this.km.add(distance);
    }
    @Override
    public void addFuel(BigDecimal liter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFuel'");
    }
}
