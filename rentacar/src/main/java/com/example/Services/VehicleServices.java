package com.example.Services;
import com.example.DAO.AutomobileDao;
import com.example.DAO.HelicopterDao; 

import com.example.DAO.MotorcycleDao;
import com.example.DAO.VehicleDao;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Entities.DbModels.Vehicles.Vehicle;

import com.example.Utils.Annotations.VehiclesTransactional;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class VehicleServices {

    private final VehicleDao vehicleDao;
    private final AutomobileDao automobileDao;
    private final MotorcycleDao motorcycleDao;
    private final HelicopterDao helicopterDao; 

    @Autowired
    public VehicleServices(VehicleDao vehicleDao, AutomobileDao automobileDao, MotorcycleDao motorcycleDao, HelicopterDao helicopterDao) { 
        this.vehicleDao = vehicleDao;
        this.automobileDao = automobileDao;
        this.motorcycleDao = motorcycleDao;
        this.helicopterDao = helicopterDao;
    }

    @VehiclesTransactional
    public Vehicle createAutomobile(Automobile automobile) {
        
        if (automobileDao.findByPlate(automobile.getPlate()).isPresent()) {
            throw new IllegalStateException("An automobile with this plate already exists: " + automobile.getPlate());
        }
        
        Automobile savedAutomobile = automobileDao.save(automobile);
        
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleStatus(VehicleStatus.AVAILABLE);
        vehicle.setDetailTableType(VehicleTypes.AUTOMOBILE);
        vehicle.setDetailTableId(savedAutomobile.getId());

        return vehicleDao.save(vehicle);
    }

    @VehiclesTransactional
    public Vehicle createMotorcycle(Motorcycle motorcycle) {
        // Benzersizlik kontrolü
        if (motorcycleDao.findByPlate(motorcycle.getPlate()).isPresent()) {
            throw new IllegalStateException("A motorcycle with this plate already exists: " + motorcycle.getPlate());
        }

        Motorcycle savedMotorcycle = motorcycleDao.save(motorcycle);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleStatus(VehicleStatus.AVAILABLE);
        vehicle.setDetailTableType(VehicleTypes.MOTORCYCLE);
        vehicle.setDetailTableId(savedMotorcycle.getId());

        return vehicleDao.save(vehicle);
    }

    @VehiclesTransactional
    public Vehicle createHelicopter(Helicopter helicopter) {
        // Benzersizlik kontrolü
        if (helicopterDao.findByTailNumber(helicopter.getTailNumber()).isPresent()) {
            throw new IllegalStateException("A helicopter with this tail number already exists: " + helicopter.getTailNumber());
        }

        Helicopter savedHelicopter = helicopterDao.save(helicopter);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleStatus(VehicleStatus.AVAILABLE);
        vehicle.setDetailTableType(VehicleTypes.HELICOPTER);
        vehicle.setDetailTableId(savedHelicopter.getId());

        return vehicleDao.save(vehicle);
    }

    @VehiclesTransactional
    public void deleteAutomobile(Integer autoId) {
        Vehicle vehicle = vehicleDao
            .findByDetailTableTypeAndDetailTableId(VehicleTypes.AUTOMOBILE, autoId)
            .orElseThrow(() -> new IllegalStateException("Parent Vehicle not found for automobile id: " + autoId));
        
        vehicleDao.delete(vehicle);
        automobileDao.deleteById(autoId);
    }

    @VehiclesTransactional
    public void deleteMotorcycle(Integer motorId) {
        Vehicle vehicle = vehicleDao
            .findByDetailTableTypeAndDetailTableId(VehicleTypes.MOTORCYCLE, motorId)
            .orElseThrow(() -> new IllegalStateException("Parent Vehicle not found for motorcycle id: " + motorId));
            
        vehicleDao.delete(vehicle);
        motorcycleDao.deleteById(motorId);
    }

    @VehiclesTransactional
    public void deleteHelicopter(Integer heliId) {
        Vehicle vehicle = vehicleDao
            .findByDetailTableTypeAndDetailTableId(VehicleTypes.HELICOPTER, heliId)
            .orElseThrow(() -> new IllegalStateException("Parent Vehicle not found for helicopter id: " + heliId));
            
        vehicleDao.delete(vehicle);
        helicopterDao.deleteById(heliId);
    }


    public Optional<Automobile> getAutomobileByPlate(String plate) {
        return automobileDao.findByPlate(plate);
    }

    public Optional<Motorcycle> getMotorcycleByPlate(String plate) {
        return motorcycleDao.findByPlate(plate);
    }
    
    public Optional<Helicopter> getHelicopterByTailNumber(String tailNumber) {
        return helicopterDao.findByTailNumber(tailNumber);
    }
     @VehiclesTransactional
    public Automobile updateAutomobile(Automobile automobile) {
        if (automobile.getId() == null || !automobileDao.existsById(automobile.getId())) {
            throw new IllegalArgumentException("Automobile with ID " + automobile.getId() + " not found for update.");
        }
        return automobileDao.save(automobile);
    }

    @VehiclesTransactional
    public Motorcycle updateMotorcycle(Motorcycle motorcycle) {
        if (motorcycle.getId() == null || !motorcycleDao.existsById(motorcycle.getId())) {
            throw new IllegalArgumentException("Motorcycle with ID " + motorcycle.getId() + " not found for update.");
        }
        return motorcycleDao.save(motorcycle);
    }

    @VehiclesTransactional
    public Helicopter updateHelicopter(Helicopter helicopter) {
        if (helicopter.getId() == null || !helicopterDao.existsById(helicopter.getId())) {
            throw new IllegalArgumentException("Helicopter with ID " + helicopter.getId() + " not found for update.");
        }
        return helicopterDao.save(helicopter);
    }

   
}