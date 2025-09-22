package com.example.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.MotorcycleDao;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Utils.Enums.MotorcycleMobility;

@Service
public class MotorcycleServices { // Senin isimlendirme stilinle uyumlu olması için çoğul değil tekil isim kullanıyorum

    private final MotorcycleDao motorcycleDao;

    @Autowired
    public MotorcycleServices(MotorcycleDao motorcycleDao) {
        this.motorcycleDao = motorcycleDao;
    }

    
    @Transactional
    public Motorcycle saveNewMotorcycle(Motorcycle newMotorcycle) {
        // İş mantığı: Plaka unique olmalı.
        if (motorcycleDao.findByPlate(newMotorcycle.getPlate()).isPresent()) {
            throw new IllegalStateException("Plate " + newMotorcycle.getPlate() + " is already taken.");
        }
        return motorcycleDao.save(newMotorcycle);
    }

   
    @Transactional(readOnly = true)
    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleDao.findAll();
    }

    
    public Motorcycle getMotorcycleById(Integer id) {
        return motorcycleDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Motorcycle not found with ID: " + id));
    }

   
    public Motorcycle getMotorcycleByPlate(String plate) {
        return motorcycleDao.findByPlate(plate)
                .orElseThrow(() -> new IllegalStateException("Motorcycle not found with plate: " + plate));
    }

   
    public List<Motorcycle> getMotorcyclesByMobilityType(MotorcycleMobility mobilityType) {
        return motorcycleDao.findByMobilityType(mobilityType);
    }

  
    public List<Motorcycle> getMotorcyclesWithEngineCcGreaterThan(Integer cc) {
        return motorcycleDao.findByEngineCCGreaterThan(cc);
    }

   
    @Transactional
    public void deleteMotorcycle(Integer id) {
        if (!motorcycleDao.existsById(id)) {
            throw new IllegalStateException("Motorcycle to delete not found with ID: " + id);
        }
        motorcycleDao.deleteById(id);
    }
    
    @Transactional
    public Motorcycle updateMotorcycle(Motorcycle motorcycleToUpdate) {
         
        Motorcycle existingMotorcycle = motorcycleDao.findById(motorcycleToUpdate.getId())
                 .orElseThrow(() -> new IllegalStateException("Motorcycle to update not found with ID: " + motorcycleToUpdate.getId()));
        
        
        if (!existingMotorcycle.getPlate().equals(motorcycleToUpdate.getPlate())) {
            if (motorcycleDao.findByPlate(motorcycleToUpdate.getPlate()).isPresent()) {
                throw new IllegalStateException("Plate " + motorcycleToUpdate.getPlate() + " is already taken by another vehicle.");
            }
        }

        return motorcycleDao.save(motorcycleToUpdate);
    }
}