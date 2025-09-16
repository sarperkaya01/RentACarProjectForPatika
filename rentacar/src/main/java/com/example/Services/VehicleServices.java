package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.VehicleDao;
import com.example.DAO.VehiclePropertiesDao;
import com.example.Entities.DbModels.Vehicles.Vehicle;
import com.example.Utils.Enums.VehicleStatus;


@Service
public class VehicleServices {
    private final VehicleDao vDao;
    private final VehiclePropertiesDao vPropertiesDao;
    public VehicleServices(VehicleDao vDao, VehiclePropertiesDao vPropertiesDao) {
        this.vDao = vDao;
        this.vPropertiesDao = vPropertiesDao;
    }
    @Transactional
    public Vehicle createVehicle(Vehicle newVehicle) {
        
        if (vDao.findByPlate(newVehicle.getPlate()).isPresent()) {
            throw new IllegalStateException("Bu plakaya sahip bir araç zaten mevcut: " + newVehicle.getPlate());
        }

       
        if (!vPropertiesDao.existsById(newVehicle.getPropId())) {
            throw new IllegalStateException("Araç oluşturulamaz. Geçersiz özellik ID'si: " + newVehicle.getPropId());
        }

        return vDao.save(newVehicle);
    }
     @Transactional
    public Vehicle updateVehicle(Vehicle vehicleToUpdate) {
        // 1. Güncellenecek aracın ID'sinin null olmadığından ve veritabanında var olduğundan emin ol.
        vDao.findById(vehicleToUpdate.getVehicleId())
                .orElseThrow(() -> new IllegalStateException("Güncelleme başarısız. Araç bulunamadı. ID: " + vehicleToUpdate.getVehicleId()));

        // 2. Eğer typeId değiştiriliyorsa, yeni ID'nin geçerli olup olmadığını kontrol et.
        if (!vPropertiesDao.existsById(vehicleToUpdate.getPropId())) {
            throw new IllegalStateException("Güncelleme başarısız. Geçersiz özellik ID'si: " + vehicleToUpdate.getPropId());
        }
        
        return vDao.save(vehicleToUpdate);
    }
     @Transactional
    public void deleteVehicle(Integer id) {
        if (!vDao.existsById(id)) {
            throw new IllegalStateException("Silme başarısız. Araç bulunamadı. ID: " + id);
        }
        vDao.deleteById(id);
    }
      public Vehicle getVehicleById(Integer id) {
        return vDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Araç bulunamadı. ID: " + id));
    }

    public List<Vehicle> getAllVehicles() {
        return vDao.findAll();
    }
    
    public Optional<Vehicle> getVehicleByPlate(String plate) {
        return vDao.findByPlate(plate);
    }

    public List<Vehicle> getVehiclesByPropertyId(Integer propId) {
        return vDao.findByVehiclePropertyId(propId);
    }
    
    public List<Vehicle> getVehiclesByModelYear(Integer modelYear) {
        return vDao.findByModelYear(modelYear);
    }
    
    public List<Vehicle> getVehiclesByModelName(String modelName) {
        return vDao.findByModelName(modelName);
    }
    
    public List<Vehicle> getVehiclesByBrandName(String brandName) {
        return vDao.findByBrandName(brandName);
    }
    
    public List<Vehicle> getVehiclesByStatus(VehicleStatus vehicleStatus) {
        return vDao.findByVehicleStatus(vehicleStatus);
    }
    
    public List<Vehicle> getVehiclesByValueBetween(Integer minValue, Integer maxValue) {
        return vDao.findByVehicleValueBetween(minValue, maxValue);
    }


    

}
