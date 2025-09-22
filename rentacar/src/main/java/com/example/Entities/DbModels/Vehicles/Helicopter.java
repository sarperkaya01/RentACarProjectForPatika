package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;


import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Interfaces.AirVehicle;
import com.example.Utils.Interfaces.FuelDriven;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "helicopters")
@DiscriminatorValue("HELICOPTER") // Discriminator sütununa yazılacak değer
@PrimaryKeyJoinColumn(name = "heli_id")

public class Helicopter extends Vehicle implements AirVehicle, FuelDriven {

    @Column(name = "tail_number", nullable = false, unique = true, length = 15)
    private String tailNumber;

    @Column(name = "flight_hours", nullable = false, precision = 12, scale = 2)
    private BigDecimal flightHours;

    @Column(name = "current_fuel", nullable = false, precision = 8, scale = 2)
    private BigDecimal currentFuel;

    @Column(name = "max_fuel_capacity", nullable = false, precision = 8, scale = 2)
    private BigDecimal maxFuelCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "speciality", nullable = false, length = 20)
    private HeliSpeciality speciality;

    // --- Arayüz Metotlarının Uygulanması ---

    @Override
    public String getTailNumber() {
        return this.tailNumber;
    }

    @Override
    public BigDecimal getFlightHours() {
        return this.flightHours;
    }

    @Override
    public void addFlightHours(BigDecimal hours) {
        this.flightHours = this.flightHours.add(hours);
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

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public void setFlightHours(BigDecimal flightHours) {
        this.flightHours = flightHours;
    }

    public void setCurrentFuel(BigDecimal currentFuel) {
        this.currentFuel = currentFuel;
    }

    public void setMaxFuelCapacity(BigDecimal maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }

    public HeliSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(HeliSpeciality speciality) {
        this.speciality = speciality;
    }

}
