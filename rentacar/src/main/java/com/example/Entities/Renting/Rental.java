package com.example.Entities.Renting;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import com.example.Utils.Enums.RentalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId; 

   
    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

   
    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate; 

    @Column(name = "planned_dropoff_date")
    private LocalDateTime plannedDropoffDate;        
   
    @Column(name = "planned_price", precision = 10, scale = 2)
    private BigDecimal plannedPrice;    
    
    @Column(name = "depozit", precision = 10, scale = 2) 
    private BigDecimal deposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status", nullable = false, length = 10)
    private RentalStatus rentalStatus; 
    
    
    

    public Rental() {
    }

    @Override
    public String toString() {
        return "Rental [rentalId=" + rentalId + ", vehicleId=" + vehicleId + ", customerId=" + customerId
                + ", rentDate=" + rentDate + ", plannedDropoffDate=" + plannedDropoffDate + ", plannedPrice="
                + plannedPrice + ", deposit=" + deposit + ", rentalStatus=" + rentalStatus + "]";
    }

    public Integer getRentalId() {
        return rentalId;
    }


    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }


    public Integer getVehicleId() {
        return vehicleId;
    }


    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }


    public Integer getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public LocalDateTime getRentDate() {
        return rentDate;
    }


    public void setRentDate(LocalDateTime rentDate) {
        this.rentDate = rentDate;
    }


    public LocalDateTime getPlannedDropoffDate() {
        return plannedDropoffDate;
    }

    public void setPlannedDropoffDate(LocalDateTime plannedDropoffDate) {
        this.plannedDropoffDate = plannedDropoffDate;
    }
   
    public BigDecimal getPlannedPrice() {
        return plannedPrice;
    }

    public void setPlannedPrice(BigDecimal plannedPrice) {
        this.plannedPrice = plannedPrice;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }


    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }


    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }


    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
    
    
    
}
