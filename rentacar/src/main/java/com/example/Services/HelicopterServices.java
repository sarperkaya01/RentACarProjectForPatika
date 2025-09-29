package com.example.Services;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.HelicopterDao;
import com.example.DAO.VehicleDao;
import com.example.DTO.HelicopterInfoDto;
import com.example.DTO.VehicleListDto;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Utils.Enums.HeliSpeciality;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.VehicleTypes;

@Service
public class HelicopterServices {
    private final HelicopterDao helicopterDao;
    private final VehicleDao vehicleDao;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    @Autowired
    public HelicopterServices(HelicopterDao helicopterDao,
                              VehicleDao vehicleDao,
                              VehiclePropertiesServices vehiclePropertiesServices) {
        this.helicopterDao = helicopterDao;
        this.vehicleDao = vehicleDao;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

    // --- SELECT METHODS ---

    @Transactional(readOnly = true)
    public List<VehicleListDto> getAllHelicoptersAsSummaryDto() {
        return vehicleDao.findAllAsVehicleListDto()
                .stream()
                .filter(dto -> dto.getType() == VehicleTypes.HELICOPTER)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VehicleListDto> getAllHelicoptersAsListDto() {
        return helicopterDao.findAllAsListDto();
    }

    @Transactional(readOnly = true)
    public Optional<HelicopterInfoDto> getHelicopterByPlateOrTailNumberAsInfoDto(String identifier) {
        return helicopterDao.findByPlateOrTailNumberAsInfoDto(identifier);
    }

    @Transactional(readOnly = true)
    public List<HelicopterInfoDto> getHelicoptersByBrandNameAsInfoDto(String brandName) {
        return helicopterDao.findByBrandNameAsInfoDto(brandName);
    }

    @Transactional(readOnly = true)
    public List<HelicopterInfoDto> getHelicoptersByModelNameAsInfoDto(String modelName) {
        return helicopterDao.findByModelNameAsInfoDto(modelName);
    }

    @Transactional(readOnly = true)
    public List<HelicopterInfoDto> getHelicoptersByModelYearAsInfoDto(Integer modelYear) {
        return helicopterDao.findByModelYearAsInfoDto(modelYear);
    }

    @Transactional(readOnly = true)
    public List<HelicopterInfoDto> getHelicoptersByFlightHoursGreaterThanAsInfoDto(BigDecimal hours) {
        return helicopterDao.findByFlightHoursGreaterThanAsInfoDto(hours);
    }

    @Transactional(readOnly = true)
    public List<HelicopterInfoDto> getHelicoptersBySpecialityAsInfoDto(HeliSpeciality speciality) {
        return helicopterDao.findBySpecialityAsInfoDto(speciality);
    }

    // --- BASIC CRUD ---

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
        Helicopter helicopterToDelete = getHelicopterById(id);

        if (helicopterToDelete.getVehicleStatus() == VehicleStatus.RENTED) {
            throw new IllegalStateException(
                    "Cannot delete helicopter with ID " + id + " because it is currently rented.");
        }

        helicopterDao.delete(helicopterToDelete);
    }

    // --- UPDATE METHODS ---

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
