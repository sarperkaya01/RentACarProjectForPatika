package com.example.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.VehiclePropertiesDao;

import com.example.Entities.DbModels.Vehicles.VehicleProperties;

@Service
public class VehiclePropertiesServices {
    private final VehiclePropertiesDao propertiesDao;

    @Autowired
    public VehiclePropertiesServices(VehiclePropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
    }

    public VehicleProperties getPropertiesById(Integer id) {
        return propertiesDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Özellik bulunamadı. ID: " + id));
    }

    @Transactional
    public VehicleProperties newVehicleProperties(VehicleProperties vp) {
        return propertiesDao.save(vp);
    }

    @Transactional
    public VehicleProperties updateVehicleProperties(VehicleProperties propertiesToUpdate) {
        if (propertiesToUpdate.getPropId() == null) {
            throw new IllegalArgumentException("Güncelleme için ID boş olamaz.");
        }

        if (!propertiesDao.existsById(propertiesToUpdate.getPropId())) {
            throw new IllegalStateException(
                    "Güncelleme başarısız. Özellik bulunamadı. ID: " + propertiesToUpdate.getPropId());
        }

        return propertiesDao.save(propertiesToUpdate);
    }

    @Transactional
    public void deleteVehicleProperties(Integer id) {
        if (!propertiesDao.existsById(id)) {
            throw new IllegalStateException("Silme başarısız. Özellik bulunamadı. ID: " + id);
        }

        propertiesDao.deleteById(id);
    }
}
