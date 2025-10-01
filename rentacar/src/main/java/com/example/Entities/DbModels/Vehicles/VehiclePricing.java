package com.example.Entities.DbModels.Vehicles;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle_pricing")
public class VehiclePricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Integer priceId;

    @Column(name = "vehicle_speciality", nullable = false, unique = true, length = 15)
    private String vehicleSpeciality;

    @Column(name = "daily_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal dailyPricing;

    @Column(name = "weekly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal weeklyPricing;

    @Column(name = "monthly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal monthlyPricing;

    // Getters and Setters

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public String getVehicleSpeciality() {
        return vehicleSpeciality;
    }

    public void setVehicleSpeciality(String vehicleSpeciality) {
        this.vehicleSpeciality = vehicleSpeciality;
    }

    public BigDecimal getDailyPricing() {
        return dailyPricing;
    }

    public void setDailyPricing(BigDecimal dailyPricing) {
        this.dailyPricing = dailyPricing;
    }

    public BigDecimal getWeeklyPricing() {
        return weeklyPricing;
    }

    public void setWeeklyPricing(BigDecimal weeklyPricing) {
        this.weeklyPricing = weeklyPricing;
    }

    public BigDecimal getMonthlyPricing() {
        return monthlyPricing;
    }

    public void setMonthlyPricing(BigDecimal monthlyPricing) {
        this.monthlyPricing = monthlyPricing;
    }

}
