package com.example.Entities.DbModels.Vehicles;



import java.math.BigDecimal;

import com.example.Utils.Enums.VehicleTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;


@Entity
@Table(name = "vehicleproperties")
public class VehicleProperties {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_id")
    private Integer propId;

    @Column(name = "specific_vehicle_id", nullable = false, unique = true)
    private Integer specificVehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleTypes vehicleType;

    @Column(name = "hourly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal hourlyPricing;

    @Column(name = "daily_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal dailyPricing;

    @Column(name = "weekly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal weeklyPricing; 

    @Column(name = "monthly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal monthlyPricing;

    public VehicleProperties(){}
    
    @Override
    public String toString() {
        return "VehicleType [propId=" + propId + ", vehicleType=" + vehicleType + ", hourlyPricing=" + hourlyPricing
                + ", dailyPricing=" + dailyPricing + ", weeklyPricing=" + weeklyPricing + ", monthlyPricing="
                + monthlyPricing + "]";
    }
    public Integer getPropId() {
        return propId;
    }
    public void setPropId(Integer propId) {
        this.propId = propId;
    }
    public VehicleTypes getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleTypes vehicleType) {
        this.vehicleType = vehicleType;
    }
    public BigDecimal getHourlyPricing() {
        return hourlyPricing;
    }
    public void setHourlyPricing(BigDecimal hourlyPricing) {
        this.hourlyPricing = hourlyPricing;
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
