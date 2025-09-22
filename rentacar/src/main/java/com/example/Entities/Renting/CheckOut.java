package com.example.Entities.Renting;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.Utils.Enums.CheckoutStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout") // Tablo adını küçük harfle belirtmek iyi bir pratiktir.
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkout_id")
    private Integer checkoutId;

    @OneToOne(mappedBy = "checkout")
    private Rental rental;    

    @Column(name = "planned_dropoff_date")
    private LocalDateTime plannedDropoffDate;

    @Column(name = "actual_dropoff_date")
    private LocalDateTime actualDropoffDate;

    @Column(name = "planned_price", precision = 10, scale = 2)
    private BigDecimal plannedPrice;

    @Column(name = "depozit", precision = 10, scale = 2)
    private BigDecimal deposit;

    @Column(name = "late_fee", precision = 10, scale = 2)
    private BigDecimal lateFee;

    @Column(name = "repair_fee", precision = 10, scale = 2)
    private BigDecimal repairFee;

    @Column(name = "checkout_amount", precision = 10, scale = 2)
    private BigDecimal checkoutAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "checkout_status", nullable = false, length = 10)
    private CheckoutStatus checkoutStatus;


    public Integer getCheckoutId() {
        return checkoutId;
    }


    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }



    public LocalDateTime getPlannedDropoffDate() {
        return plannedDropoffDate;
    }


    public void setPlannedDropoffDate(LocalDateTime plannedDropoffDate) {
        this.plannedDropoffDate = plannedDropoffDate;
    }


    public LocalDateTime getActualDropoffDate() {
        return actualDropoffDate;
    }


    public void setActualDropoffDate(LocalDateTime actualDropoffDate) {
        this.actualDropoffDate = actualDropoffDate;
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


    public BigDecimal getLateFee() {
        return lateFee;
    }


    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }


    public BigDecimal getRepairFee() {
        return repairFee;
    }


    public void setRepairFee(BigDecimal repairFee) {
        this.repairFee = repairFee;
    }


    public BigDecimal getCheckoutAmount() {
        return checkoutAmount;
    }


    public void setCheckoutAmount(BigDecimal checkoutAmount) {
        this.checkoutAmount = checkoutAmount;
    }


    public CheckoutStatus getCheckoutStatus() {
        return checkoutStatus;
    }


    public void setCheckoutStatus(CheckoutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }


    

   
    public Checkout() {
    }

    
}
