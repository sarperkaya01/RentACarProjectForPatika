package com.example.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.AutomobileDao;
import com.example.Entities.DbModels.Vehicles.Automobile;
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

    
    public List<Automobile> getAutomobilesByDriveType(WheelDriveType driveType) {
        return automobileDao.findByWheelDriveType(driveType);
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

}
