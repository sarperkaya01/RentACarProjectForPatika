package com.example.Services;

import com.example.DAO.AutomobileDao;
import com.example.DAO.HelicopterDao;

import com.example.DAO.MotorcycleDao;
import com.example.DAO.VehicleDao;
import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Entities.DbModels.Vehicles.Vehicle;

import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.MotorcycleMobility;
import com.example.Utils.Enums.VehicleStatus;

import com.example.Utils.Enums.WheelDriveType;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleDao vehicleDao;
    private final AutomobileDao automobileDao;
    private final HelicopterDao helicopterDao;
    private final MotorcycleDao motorcycleDao;

    @Autowired
    public VehicleService(VehicleDao vehicleDao, AutomobileDao automobileDao,
            HelicopterDao helicopterDao, MotorcycleDao motorcycleDao) {
        this.vehicleDao = vehicleDao;
        this.automobileDao = automobileDao;
        this.helicopterDao = helicopterDao;
        this.motorcycleDao = motorcycleDao;
    }

    @Transactional
    public Vehicle saveNewVehicle(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

    @Transactional
    public Vehicle updateVehicleStatus(Integer vehicleId, VehicleStatus newStatus) {
        Vehicle vehicleToUpdate = getVehicleById(vehicleId);
        vehicleToUpdate.setVehicleStatus(newStatus);
        return vehicleDao.save(vehicleToUpdate);
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Integer id) {
        return vehicleDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Vehicle not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<VehicleListDto> getAllVehiclesAsDto() {
        return vehicleDao.findAllAsVehicleListDto();
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByPropertyId(Integer propId) {
        return vehicleDao.findByProperties_PropId(propId);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByModelYear(Integer modelYear) {
        return vehicleDao.findByModelYear(modelYear);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByModelName(String modelName) {
        return vehicleDao.findByModelName(modelName);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByBrandName(String brandName) {
        return vehicleDao.findByBrandName(brandName);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByStatus(VehicleStatus vehicleStatus) {
        return vehicleDao.findByVehicleStatus(vehicleStatus);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByValueBetween(Integer minValue, Integer maxValue) {
        return vehicleDao.findByVehicleValueBetween(minValue, maxValue);
    }

    @Transactional(readOnly = true)
    public List<Automobile> getAutomobilesByDriveType(WheelDriveType driveType) {
        return automobileDao.findByWheelDriveType(driveType);
    }

    @Transactional(readOnly = true)
    public Automobile getAutomobileByPlate(String plate) {
        return automobileDao.findByPlate(plate)
                .orElseThrow(() -> new IllegalStateException("Automobile not found with plate: " + plate));
    }

    @Transactional(readOnly = true)
    public List<Helicopter> getHelicoptersBySpeciality(HeliSpeciality speciality) {
        return helicopterDao.findBySpeciality(speciality);
    }

    @Transactional(readOnly = true)
    public Helicopter getHelicopterByTailNumber(String tailNumber) {
        return helicopterDao.findByTailNumber(tailNumber)
                .orElseThrow(() -> new IllegalStateException("Helicopter not found with tail number: " + tailNumber));
    }

    @Transactional(readOnly = true)
    public List<Helicopter> getHelicoptersWithFlightHoursGreaterThan(BigDecimal hours) {
        return helicopterDao.findByFlightHoursGreaterThan(hours);
    }

    @Transactional(readOnly = true)
    public List<Motorcycle> getMotorcyclesWithEngineCcGreaterThan(Integer cc) {
        return motorcycleDao.findByEngineCCGreaterThan(cc);
    }

    @Transactional(readOnly = true)
    public Motorcycle getMotorcycleByPlate(String plate) {
        return motorcycleDao.findByPlate(plate)
                .orElseThrow(() -> new IllegalStateException("Motorcycle not found with plate: " + plate));
    }

    @Transactional(readOnly = true)
    public List<Motorcycle> getMotorcyclesByMobilityType(MotorcycleMobility mobilityType) {
        return motorcycleDao.findByMobilityType(mobilityType);
    }
}