package com.example.Entities.DbModels.Vehicles;

import com.example.Utils.Enums.VehicleStatus;
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
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status", nullable = false, length = 20)
    private VehicleStatus vehicleStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "detail_table_type", nullable = false, length = 20)
    private VehicleTypes detailTableType;

    @Column(name = "detail_table_id", nullable = false)
    private Integer detailTableId;

    // --- Getter'lar ve Setter'lar ---
    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicled(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public VehicleTypes getDetailTableType() {
        return detailTableType;
    }

    public void setDetailTableType(VehicleTypes detailTableType) {
        this.detailTableType = detailTableType;
    }

    public Integer getDetailTableId() {
        return detailTableId;
    }

    public void setDetailTableId(Integer detailTableId) {
        this.detailTableId = detailTableId;
    }
}
