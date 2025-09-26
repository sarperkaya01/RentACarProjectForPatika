package com.example.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.HelicopterDao;
import com.example.DTO.HelicopterDto;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.VehicleStatus;

@Service
public class HelicopterServices {
    private final HelicopterDao helicopterDao;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    @Autowired
    public HelicopterServices(HelicopterDao helicopterDao, VehiclePropertiesServices vehiclePropertiesServices) {
        this.helicopterDao = helicopterDao;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

    // --- Public Methods Returning DTOs (for Controllers/Views) ---

    @Transactional(readOnly = true)
    public List<HelicopterDto> getAllHelicoptersAsDto() {
        return helicopterDao.findAllHelicoptersAsDto();
    }

    @Transactional(readOnly = true)
    public Optional<HelicopterDto> getHelicopterByPlateOrTailNumberAsDto(String identifier) {
        return helicopterDao.findByPlateOrTailNumberAsDto(identifier);
    }

    @Transactional(readOnly = true)
    public List<HelicopterDto> getHelicoptersByBrandNameAsDto(String brandName) {
        return helicopterDao.findByBrandNameAsDto(brandName);
    }

    @Transactional(readOnly = true)
    public List<HelicopterDto> getHelicoptersByModelNameAsDto(String modelName) {
        return helicopterDao.findByModelNameAsDto(modelName);
    }

    @Transactional(readOnly = true)
    public List<HelicopterDto> getHelicoptersByModelYearAsDto(Integer modelYear) {
        return helicopterDao.findByModelYearAsDto(modelYear);
    }

    @Transactional(readOnly = true)
    public List<HelicopterDto> getHelicoptersByFlightHoursGreaterThanAsDto(BigDecimal hours) {
        return helicopterDao.findByFlightHoursGreaterThanAsDto(hours);
    }

    @Transactional(readOnly = true)
    public List<HelicopterDto> getHelicoptersBySpecialityAsDto(HeliSpeciality speciality) {
        return helicopterDao.findBySpecialityAsDto(speciality);
    }

    // --- Entity-Level Operations (for internal use, updates, deletes) ---

    public Helicopter getHelicopterById(Integer id) {
        return helicopterDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Helicopter not found with ID: " + id));
    }

    @Transactional
    public Helicopter saveNewHelicopter(Helicopter newHelicopter) {
        helicopterDao.findByPlateOrTailNumber(newHelicopter.getPlateOrTailNumber())
                .ifPresent(h -> {
                    throw new IllegalStateException(
                            "Tail number " + newHelicopter.getPlateOrTailNumber() + " is already taken.");
                });
        return helicopterDao.save(newHelicopter);
    }

    @Transactional
    public void deleteHelicopter(Integer id) {
        if (!helicopterDao.existsById(id)) {
            throw new IllegalStateException("Helicopter to delete not found with ID: " + id);
        }
        helicopterDao.deleteById(id);
    }

    // --- Dynamic Update Methods ---

    @Transactional
    public Helicopter updateBrandName(Integer helicopterId, String newBrandName) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setBrandName(newBrandName);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateModelName(Integer helicopterId, String newModelName) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setModelName(newModelName);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateModelYear(Integer helicopterId, Integer newModelYear) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setModelYear(newModelYear);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updatePlateOrTailNumber(Integer helicopterId, String newIdentifier) {
        if (newIdentifier == null || newIdentifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be null or empty.");
        }
        Helicopter helicopter = getHelicopterById(helicopterId);

        if (!helicopter.getPlateOrTailNumber().equalsIgnoreCase(newIdentifier)
                && helicopterDao.findByPlateOrTailNumber(newIdentifier).isPresent()) {
            throw new IllegalStateException("Identifier " + newIdentifier + " is already in use by another vehicle.");
        }

        helicopter.setPlateOrTailNumber(newIdentifier);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateCurrentFuel(Integer helicopterId, BigDecimal newCurrentFuel) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setCurrentFuel(newCurrentFuel);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateMaxFuelCapacity(Integer helicopterId, BigDecimal newMaxCapacity) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setMaxFuelCapacity(newMaxCapacity);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateVehicleValue(Integer helicopterId, Integer newVehicleValue) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setVehicleValue(newVehicleValue);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateVehicleStatus(Integer helicopterId, VehicleStatus newStatus) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setVehicleStatus(newStatus);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateFlightHours(Integer helicopterId, BigDecimal newFlightHours) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setFlightHours(newFlightHours);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateSpeciality(Integer helicopterId, HeliSpeciality newSpeciality) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        helicopter.setSpeciality(newSpeciality);
        return helicopterDao.save(helicopter);
    }

    @Transactional
    public Helicopter updateDailyPricing(Integer helicopterId, BigDecimal newDailyPricing) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        vehiclePropertiesServices.updateDailyPricing(helicopter.getProperties().getPropId(), newDailyPricing);
        return helicopter;
    }

    @Transactional
    public Helicopter updateWeeklyPricing(Integer helicopterId, BigDecimal newWeeklyPricing) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        vehiclePropertiesServices.updateWeeklyPricing(helicopter.getProperties().getPropId(), newWeeklyPricing);
        return helicopter;
    }

    @Transactional
    public Helicopter updateMonthlyPricing(Integer helicopterId, BigDecimal newMonthlyPricing) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        vehiclePropertiesServices.updateMonthlyPricing(helicopter.getProperties().getPropId(), newMonthlyPricing);
        return helicopter;
    }
}
