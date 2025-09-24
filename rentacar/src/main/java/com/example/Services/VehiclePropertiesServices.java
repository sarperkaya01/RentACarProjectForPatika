package com.example.Services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.VehiclePropertiesDao;

import com.example.Entities.DbModels.Vehicles.VehicleProperties;

@Service
public class VehiclePropertiesServices {
    private final VehiclePropertiesDao vehiclePropertiesDao;

    @Autowired
    public VehiclePropertiesServices(VehiclePropertiesDao vehiclePropertiesDao) {
        this.vehiclePropertiesDao = vehiclePropertiesDao;
    }

    public VehicleProperties getPropertiesById(Integer id) {
        return vehiclePropertiesDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Özellik bulunamadı. ID: " + id));
    }

    @Transactional
    public VehicleProperties saveNewProperties(VehicleProperties vp) {
        return vehiclePropertiesDao.save(vp);
    }

   @Transactional
    public VehicleProperties updateDailyPricing(Integer propId, BigDecimal newDailyPricing) {
        VehicleProperties properties = getPropertiesById(propId);
        properties.setDailyPricing(newDailyPricing);
        return vehiclePropertiesDao.save(properties);
    }

    @Transactional
    public VehicleProperties updateWeeklyPricing(Integer propId, BigDecimal newWeeklyPricing) {
        VehicleProperties properties = getPropertiesById(propId);
        properties.setWeeklyPricing(newWeeklyPricing);
        return vehiclePropertiesDao.save(properties);
    }

    @Transactional
    public VehicleProperties updateMonthlyPricing(Integer propId, BigDecimal newMonthlyPricing) {
        VehicleProperties properties = getPropertiesById(propId);
        properties.setMonthlyPricing(newMonthlyPricing);
        return vehiclePropertiesDao.save(properties);
    }

    @Transactional
    public void deleteVehicleProperties(Integer id) {
        if (!vehiclePropertiesDao.existsById(id)) {
            throw new IllegalStateException("Silme başarısız. Özellik bulunamadı. ID: " + id);
        }

        vehiclePropertiesDao.deleteById(id);
    }
}
