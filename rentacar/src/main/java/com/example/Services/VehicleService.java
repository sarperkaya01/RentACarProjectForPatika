package com.example.Services;


import com.example.DAO.VehicleDao;
import com.example.DTO.AutomobileDto;
import com.example.DTO.HelicopterDto;
import com.example.DTO.MotorcycleDto;

import com.example.DTO.VehicleListDto;

import com.example.Entities.DbModels.Vehicles.Vehicle;


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

    @Transactional
    public Vehicle saveNewVehicle(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

    @Transactional
    public Vehicle updateVehicle(Vehicle vehicleToUpdate) {

        Vehicle existingVehicle = getVehicleById(vehicleToUpdate.getId());   
        
        if (vehicleToUpdate.getId() == null) {
            throw new IllegalArgumentException("Id can not be null for update.");
        }

        if (!vehicleDao.existsById(vehicleToUpdate.getId())) {
            throw new IllegalStateException(
                    "Update failed. Propertiy not found. ID: " + vehicleToUpdate.getId());
        }

        return vehicleDao.save(existingVehicle);
    }

    @Transactional
    public void deleteVehicle(Integer vehicleId) {
        if (!vehicleDao.existsById(vehicleId)) {
            throw new IllegalStateException("Vehicle not found. ID: " + vehicleId);
        }
        vehicleDao.deleteById(vehicleId);
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Integer id) {
        return vehicleDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Vehicle not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<VehicleListDto> getAllVehiclesAsDto() {
        return vehicleDao.findAllAsVehicleListDto();
    }

     @Transactional(readOnly = true)
    public List<AutomobileDto> getAllAutomobilesAsDto() {
        return vehicleDao.findAllAutomobilesAsDto();
    }

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getAllMotorcyclesAsDto() {
        return vehicleDao.findAllMotorcyclesAsDto();
    }

    @Transactional(readOnly = true)
    public List<HelicopterDto> getAllHelicoptersAsDto() {
        return vehicleDao.findAllHelicoptersAsDto();
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