package com.example.Entities.Renting;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.Utils.Enums.CheckOutStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout") // Tablo adını küçük harfle belirtmek iyi bir pratiktir.
public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkout_id")
    private Integer checkoutId;

    @OneToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @Column(name = "actual_dropoff_date")
    private LocalDateTime actualDropoffDate;

    @Column(name = "late_fee", precision = 10, scale = 2)
    private BigDecimal lateFee;

    @Column(name = "repair_fee", precision = 10, scale = 2)
    private BigDecimal repairFee;

    @Column(name = "checkout", precision = 10, scale = 2)
    private BigDecimal checkoutAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "checkout_status", nullable = false, length = 10)
    private CheckOutStatus checkoutStatus;

    @Override
    public String toString() {
        return "CheckOut [checkoutId=" + checkoutId + ", rental_Id=" + rental + ", actualDropoffDate="
                + actualDropoffDate + ", lateFee=" + lateFee + ", repairFee=" + repairFee + ", checkoutAmount="
                + checkoutAmount + ", checkoutStatus=" + checkoutStatus + "]";
    }

    public Integer getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
    

    public LocalDateTime getActualDropoffDate() {
        return actualDropoffDate;
    }

    public void setActualDropoffDate(LocalDateTime actualDropoffDate) {
        this.actualDropoffDate = actualDropoffDate;
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

    public CheckOutStatus getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(CheckOutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public CheckOut(){}
    

}
