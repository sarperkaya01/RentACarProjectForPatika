package com.example.Entities.DbModels.Vehicles;



import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", insertable = false, updatable = false)
    private VehicleTypes vehicleType;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "price_id", referencedColumnName = "price_id")
    private VehiclePricing pricing;

    @Column(name = "brand_name", nullable = false, length = 30)
    private String brandName;

    @Column(name = "model_name", nullable = false, length = 30)
    private String modelName;

    @Column(name = "model_year", nullable = false)
    private Integer modelYear;

    @Column(name = "plate_or_tailnumber", nullable = false)
    private String plateOrTailNumber;
   

    @Column(name = "vehicle_value", nullable = false)
    private Integer vehicleValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status", nullable = false, length = 20)
    private VehicleStatus vehicleStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleTypes getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypes vehicleType) {
        this.vehicleType = vehicleType;
    }


    public VehiclePricing getPricing() {
        return pricing;
    }

    public void setPricing(VehiclePricing pricing) {
        this.pricing = pricing;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getPlateOrTailNumber() {
        return plateOrTailNumber;
    }

    public void setPlateOrTailNumber(String plateOrTailNumber) {
        this.plateOrTailNumber = plateOrTailNumber;
    }

  

    public Integer getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(Integer vehicleValue) {
        this.vehicleValue = vehicleValue;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

}
