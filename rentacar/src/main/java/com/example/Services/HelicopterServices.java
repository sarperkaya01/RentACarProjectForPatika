package com.example.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.HelicopterDao;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Utils.Enums.HeliSpeciality;

@Service
public class HelicopterServices {

    private final HelicopterDao helicopterDao;

    @Autowired
    public HelicopterServices(HelicopterDao helicopterDao) {
        this.helicopterDao = helicopterDao;
    }

    @Transactional
    public Helicopter saveNewHelicopter(Helicopter newHelicopter) {
        if (helicopterDao.findByTailNumber(newHelicopter.getTailNumber()).isPresent()) {
            throw new IllegalStateException("Tail number " + newHelicopter.getTailNumber() + " is already taken.");
        }
        return helicopterDao.save(newHelicopter);
    }

    @Transactional(readOnly = true)
    public List<Helicopter> getAllHelicopters() {
        return helicopterDao.findAll();
    }

    public Helicopter getHelicopterById(Integer id) {
        return helicopterDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Helicopter not found with ID: " + id));
    }

    public Helicopter getHelicopterByTailNumber(String tailNumber) {
        return helicopterDao.findByTailNumber(tailNumber)
                .orElseThrow(() -> new IllegalStateException("Helicopter not found with tail number: " + tailNumber));
    }

    public List<Helicopter> getHelicoptersBySpeciality(HeliSpeciality speciality) {
        return helicopterDao.findBySpeciality(speciality);
    }

    public List<Helicopter> getHelicoptersWithFlightHoursGreaterThan(BigDecimal hours) {
        return helicopterDao.findByFlightHoursGreaterThan(hours);
    }

    @Transactional
    public void deleteHelicopter(Integer id) {
        if (!helicopterDao.existsById(id)) {
            throw new IllegalStateException("Helicopter to delete not found with ID: " + id);
        }
        helicopterDao.deleteById(id);
    }
    
    @Transactional
    public Helicopter updateHelicopter(Helicopter helicopterToUpdate) {
        Helicopter existingHelicopter = helicopterDao.findById(helicopterToUpdate.getId())
                 .orElseThrow(() -> new IllegalStateException("Helicopter to update not found with ID: " + helicopterToUpdate.getId()));
        
        if (!existingHelicopter.getTailNumber().equals(helicopterToUpdate.getTailNumber())) {
            if (helicopterDao.findByTailNumber(helicopterToUpdate.getTailNumber()).isPresent()) {
                throw new IllegalStateException("Tail number " + helicopterToUpdate.getTailNumber() + " is already taken by another vehicle.");
            }
        }

        return helicopterDao.save(helicopterToUpdate);
    }
}
