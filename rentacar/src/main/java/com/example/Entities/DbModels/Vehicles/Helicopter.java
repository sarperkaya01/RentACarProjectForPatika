package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;

import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Interfaces.AirVehicle;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity

@DiscriminatorValue("HELICOPTER") // Discriminator sütununa yazılacak değer


public class Helicopter extends Vehicle implements AirVehicle {

  
    @Column(name = "flight_hours", nullable = false, precision = 12, scale = 2)
    private BigDecimal flightHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "speciality", nullable = false, length = 20)
    private HeliSpeciality speciality;
   

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
    // --- Arayüz Metotlarının Uygulanması ---

    @Override
    public void addFlightHours(BigDecimal hours) {
        this.flightHours = this.flightHours.add(hours);
    }

}
