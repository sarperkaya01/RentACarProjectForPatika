package com.example.Services;


import com.example.DAO.VehicleDao;


import com.example.DTO.VehicleListDto;

import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.VehicleStatus;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

   private final VehicleDao vehicleDao;

    @Autowired
    public VehicleService(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    // --- GENEL DTO DÖNDÜREN METOTLAR ---

    @Transactional(readOnly = true)
    public List<VehicleListDto> getAllVehiclesAsDto() {
        return vehicleDao.findAllAsVehicleListDto();
    }
    
    @Transactional(readOnly = true)
    public List<VehicleListDto> getVehiclesByBrandNameAsDto(String brandName) {
        return vehicleDao.findByBrandNameAsDto(brandName);
    }

    @Transactional(readOnly = true)
    public List<VehicleListDto> getVehiclesByModelNameAsDto(String modelName) {
        return vehicleDao.findByModelNameAsDto(modelName);
    }
    
    @Transactional(readOnly = true)
    public List<VehicleListDto> getVehiclesByModelYearAsDto(Integer modelYear) {
        return vehicleDao.findByModelYearAsDto(modelYear);
    }

    @Transactional(readOnly = true)
    public List<VehicleListDto> getVehiclesByStatusAsDto(VehicleStatus status) {
        return vehicleDao.findByVehicleStatusAsDto(status);
    }

    @Transactional(readOnly = true)
    public List<VehicleListDto> getVehiclesByValueBetweenAsDto(Integer minValue, Integer maxValue) {
        return vehicleDao.findByVehicleValueBetweenAsDto(minValue, maxValue);
    }

    // --- GENEL ENTITY İŞLEMLERİ ---
    
    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Integer id) {
        return vehicleDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Vehicle not found with ID: " + id));
    }

    @Transactional
    public void deleteVehicle(Integer vehicleId) {
        if (!vehicleDao.existsById(vehicleId)) {
            throw new IllegalStateException("Vehicle to delete not found with ID: " + vehicleId);
        }
        vehicleDao.deleteById(vehicleId);
    }



}

// if (vehicleToUpdate instanceof Automobile) {
//             Automobile newAuto = (Automobile) vehicleToUpdate;
//             Automobile existingAuto = (Automobile) existingVehicle;

//             if (!existingAuto.getPlate().equals(newAuto.getPlate())
//                     && automobileDao.findByPlate(newAuto.getPlate()).isPresent()) {
//                 throw new IllegalStateException("Plate " + newAuto.getPlate() + " already taken.");
//             }

//             existingAuto.setPlate(newAuto.getPlate());
//             existingAuto.setKm(newAuto.getKm());
//             existingAuto.setBrandName(newAuto.getBrandName());
//             existingAuto.setCurrentFuel(newAuto.getCurrentFuel());
//             existingAuto.setModelName(newAuto.getModelName());
//             existingAuto.setModelYear(newAuto.getModelYear());
//             existingAuto.set

//         } else if (vehicleToUpdate instanceof Motorcycle) {
//             Motorcycle newMotor = (Motorcycle) vehicleToUpdate;
//             Motorcycle existingMotor = (Motorcycle) existingVehicle;

//             if (!existingMotor.getPlate().equals(newMotor.getPlate())
//                     && motorcycleDao.findByPlate(newMotor.getPlate()).isPresent()) {
//                 throw new IllegalStateException("Plate " + newMotor.getPlate() + " already taken.");
//             }

//             existingMotor.setPlate(newMotor.getPlate());
//             existingMotor.setKm(newMotor.getKm());
//         } else if (vehicleToUpdate instanceof Helicopter) {
//             Helicopter newHeli = (Helicopter) vehicleToUpdate;
//             Helicopter existingHeli = (Helicopter) existingVehicle;

//             if (!existingHeli.getTailNumber().equals(newHeli.getTailNumber())
//                     && helicopterDao.findByTailNumber(newHeli.getTailNumber()).isPresent()) {
//                 throw new IllegalStateException("TailNumber " + newHeli.getTailNumber() + " already taken.");
//             }

//             existingHeli.setTailNumber(newHeli.getTailNumber());
//             existingHeli.setFlightHours(newHeli.getFlightHours());
//         }