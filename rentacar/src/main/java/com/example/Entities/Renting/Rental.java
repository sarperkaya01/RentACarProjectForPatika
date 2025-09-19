package com.example.Entities.Renting;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.RentalStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status", nullable = false, length = 10)
    private RentalStatus rentalStatus;

    @OneToOne(mappedBy = "rental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CheckOut checkout;

    

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



    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }



    public LocalDateTime getRentDate() {
        return rentDate;
    }



    public void setRentDate(LocalDateTime rentDate) {
        this.rentDate = rentDate;
    }



    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }



    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }



    public CheckOut getCheckout() {
        return checkout;
    }



    public void setCheckout(CheckOut checkout) {
        this.checkout = checkout;
    }



    public Rental() {
    }

    // @Override
    // public String toString() {
       
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

      
    //     return String.format(
    //         "----------------------------------------\n" +
    //         " Rental ID       : %d\n" +
    //         " Vehicle         : %s %s (%s)\n" +
    //         " Rented On       : %s\n" +
    //         " Planned Return  : %s\n" +
    //         " Status          : %s\n" +
    //         " Price           : $%.2f",
    //         rentalId,
    //         vehicle.getBrandName(),
    //         vehicle.getModelName(),
    //         vehicle.getPlate(),
    //         rentDate.format(formatter),
    //         (plannedDropoffDate != null ? plannedDropoffDate.format(formatter) : "N/A"),
    //         rentalStatus,
    //         plannedPrice
    //     );
    // }

}
