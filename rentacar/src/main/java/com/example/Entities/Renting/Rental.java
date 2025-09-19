package com.example.Entities.Renting;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.RentalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rentalId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;   

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;   

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

    

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicle = vehicleId;
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

    public Rental() {
    }

}
