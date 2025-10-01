package com.example.Services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.VehiclePricingDao;

import com.example.Entities.DbModels.Vehicles.VehiclePricing;

@Service
public class VehiclePricingServices {
    private final VehiclePricingDao vehiclePricingDao;

    @Autowired
    public VehiclePricingServices(VehiclePricingDao vehiclePricingDao) {
        this.vehiclePricingDao = vehiclePricingDao;
    }

    public VehiclePricing getVehiclePricingById(Integer id) {
        return vehiclePricingDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Özellik bulunamadı. ID: " + id));
    }

    @Transactional
    public VehiclePricing saveNewVehiclePricing(VehiclePricing vp) {
        return vehiclePricingDao.save(vp);
    }

   @Transactional
    public VehiclePricing updateDailyPricing(Integer propId, BigDecimal newDailyPricing) {
        VehiclePricing VehiclePricing = getVehiclePricingById(propId);
        VehiclePricing.setDailyPricing(newDailyPricing);
        return vehiclePricingDao.save(VehiclePricing);
    }

    @Transactional
    public VehiclePricing updateWeeklyPricing(Integer propId, BigDecimal newWeeklyPricing) {
        VehiclePricing VehiclePricing = getVehiclePricingById(propId);
        VehiclePricing.setWeeklyPricing(newWeeklyPricing);
        return vehiclePricingDao.save(VehiclePricing);
    }

    @Transactional
    public VehiclePricing updateMonthlyPricing(Integer propId, BigDecimal newMonthlyPricing) {
        VehiclePricing VehiclePricing = getVehiclePricingById(propId);
        VehiclePricing.setMonthlyPricing(newMonthlyPricing);
        return vehiclePricingDao.save(VehiclePricing);
    }

    @Transactional
    public void deleteVehicleVehiclePricing(Integer id) {
        if (!vehiclePricingDao.existsById(id)) {
            throw new IllegalStateException("Silme başarısız. Özellik bulunamadı. ID: " + id);
        }

        vehiclePricingDao.deleteById(id);
    }
    public VehiclePricing findBySpeciality(String speciality) {
        return vehiclePricingDao.findByVehicleSpeciality(speciality)
                .orElseThrow(() -> new IllegalStateException(
                    "No pricing profile found for speciality: " + speciality));
    }
}
