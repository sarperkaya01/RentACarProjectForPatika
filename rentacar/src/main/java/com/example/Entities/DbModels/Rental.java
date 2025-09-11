package com.example.Entities.DbModels;

import java.sql.Date;

import com.example.Services.RentalStatus;


public class Rental {
    private Integer rentalId;
    private Integer vehicleId;
    private Integer customerId;
    private Date rentDate;
    private Date plannedDropoffDate;
    private Date acctualDropoffDate;
    private Double plannedPrice;
    private Double lateFee;
    private Double repairFee;
    private Double checkout;
    private Double depozit;
    private RentalStatus rentalStatus;
    @Override
    public String toString() {
        return "Rental [rentalId=" + rentalId + ", vehicleId=" + vehicleId + ", customerId=" + customerId
                + ", rentDate=" + rentDate + ", plannedDropoffDate=" + plannedDropoffDate + ", acctualDropoffDate="
                + acctualDropoffDate + ", plannedPrice=" + plannedPrice + ", lateFee=" + lateFee + ", repairFee="
                + repairFee + ", checkout=" + checkout + ", depozit=" + depozit + ", rentalStatus=" + rentalStatus
                + "]";
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
    public Date getRentDate() {
        return rentDate;
    }
    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }
    public Date getPlannedDropoffDate() {
        return plannedDropoffDate;
    }
    public void setPlannedDropoffDate(Date plannedDropoffDate) {
        this.plannedDropoffDate = plannedDropoffDate;
    }
    public Date getAcctualDropoffDate() {
        return acctualDropoffDate;
    }
    public void setAcctualDropoffDate(Date acctualDropoffDate) {
        this.acctualDropoffDate = acctualDropoffDate;
    }
    public Double getPlannedPrice() {
        return plannedPrice;
    }
    public void setPlannedPrice(Double plannedPrice) {
        this.plannedPrice = plannedPrice;
    }
    public Double getLateFee() {
        return lateFee;
    }
    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }
    public Double getRepairFee() {
        return repairFee;
    }
    public void setRepairFee(Double repairFee) {
        this.repairFee = repairFee;
    }
    public Double getCheckout() {
        return checkout;
    }
    public void setCheckout(Double checkout) {
        this.checkout = checkout;
    }
    public Double getDepozit() {
        return depozit;
    }
    public void setDepozit(Double depozit) {
        this.depozit = depozit;
    }
    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }
    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    } 
    
    
}
