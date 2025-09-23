package com.example.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.AutomobileDao;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.WheelDriveType;

@Service
public class AutomobileServices {

    private final AutomobileDao automobileDao;

    @Autowired
    public AutomobileServices(AutomobileDao automobileDao) {
        this.automobileDao = automobileDao;
    }

   
    @Transactional
    public Automobile saveNewAutomobile(Automobile newAutomobile) {
        
        if (automobileDao.findByPlate(newAutomobile.getPlate()).isPresent()) {
            throw new IllegalStateException("Plate " + newAutomobile.getPlate() + " is already taken.");
        }
        return automobileDao.save(newAutomobile);
    }

   
    @Transactional(readOnly = true) 
    public List<Automobile> getAllAutomobiles() {
        return automobileDao.findAll();
    }

    
    public Automobile getAutomobileById(Integer id) {
        return automobileDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Automobile not found with ID: " + id));
    }

   
    public Automobile getAutomobileByPlate(String plate) {
        return automobileDao.findByPlate(plate)
                .orElseThrow(() -> new IllegalStateException("Automobile not found with plate: " + plate));
    }

   
   
    @Transactional
    public void deleteAutomobile(Integer id) {
        if (!automobileDao.existsById(id)) {
            throw new IllegalStateException("Automobile to delete not found with ID: " + id);
        }
        automobileDao.deleteById(id);
    }

    @Transactional
    public Automobile updateAutomobile(Automobile automobileToUpdate) {
         
        Automobile existingAutomobile = automobileDao.findById(automobileToUpdate.getId())
                 .orElseThrow(() -> new IllegalStateException("Automobile to update not found with ID: " + automobileToUpdate.getId()));
        
        
        if (!existingAutomobile.getPlate().equals(automobileToUpdate.getPlate())) {
            if (automobileDao.findByPlate(automobileToUpdate.getPlate()).isPresent()) {
                throw new IllegalStateException("Plate " + automobileToUpdate.getPlate() + " is already taken by another vehicle.");
            }
        }

        return automobileDao.save(automobileToUpdate);
    }
    @Transactional
    public Automobile updateBrandName(Integer automobileId, String newBrandName) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setBrandName(newBrandName);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateModelName(Integer automobileId, String newModelName) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setModelName(newModelName);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateModelYear(Integer automobileId, Integer newModelYear) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setModelYear(newModelYear);
        return automobileDao.save(automobile);
    }
    
    @Transactional
    public Automobile updatePlate(Integer automobileId, String newPlate) {
        if (newPlate == null || newPlate.trim().isEmpty()) {
            throw new IllegalArgumentException("Plaka boş olamaz.");
        }
        Automobile automobile = getAutomobileById(automobileId); 

        if (!automobile.getPlate().equalsIgnoreCase(newPlate) && automobileDao.findByPlate(newPlate).isPresent()) {
            throw new IllegalStateException("Plaka " + newPlate + " zaten başka bir araç tarafından kullanılıyor.");
        }
        
        automobile.setPlate(newPlate);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateKm(Integer automobileId, BigDecimal newKm) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setKm(newKm);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateCurrentFuel(Integer automobileId, BigDecimal newCurrentFuel) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setCurrentFuel(newCurrentFuel);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateVehicleStatus(Integer automobileId, VehicleStatus newStatus) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setVehicleStatus(newStatus);
        return automobileDao.save(automobile);
    }
    
    @Transactional
    public Automobile updateWheelDriveType(Integer automobileId, WheelDriveType newWheelDriveType) {
        Automobile automobile = getAutomobileById(automobileId); 
        automobile.setWheelDriveType(newWheelDriveType);
        return automobileDao.save(automobile);
    }

}
