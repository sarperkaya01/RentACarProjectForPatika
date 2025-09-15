package com.example.Entities.DbModels.Vehicles;



import java.math.BigDecimal;

import com.example.Utils.Enums.VehicleTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "vehicletypes")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleTypes vehicleType;

    @Column(name = "hourly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal hourlyPricing;

    @Column(name = "daily_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal dailyPricing;

    @Column(name = "weekly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal weaklyPricing; 

    @Column(name = "monthly_pricing", nullable = false, precision = 6, scale = 2)
    private BigDecimal monthlyPricing;

    @Override
    public String toString() {
        return "VehicleType [typeId=" + typeId + ", vehicleType=" + vehicleType + ", hourlyPricing=" + hourlyPricing
                + ", dailyPricing=" + dailyPricing + ", weaklyPricing=" + weaklyPricing + ", monthlyPricing="
                + monthlyPricing + "]";
    }
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
    public BigDecimal getWeaklyPricing() {
        return weaklyPricing;
    }
    public void setWeaklyPricing(BigDecimal weaklyPricing) {
        this.weaklyPricing = weaklyPricing;
    }
    public BigDecimal getMonthlyPricing() {
        return monthlyPricing;
    }
    public void setMonthlyPricing(BigDecimal monthlyPricing) {
        this.monthlyPricing = monthlyPricing;
    }
}
