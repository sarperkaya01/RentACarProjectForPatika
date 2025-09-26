package com.example.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.MotorcycleDao;
import com.example.DTO.MotorcycleDto;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Utils.Enums.MotorcycleMobility;
import com.example.Utils.Enums.VehicleStatus;

@Service
public class MotorcycleServices {

    private final MotorcycleDao motorcycleDao;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    @Autowired
    public MotorcycleServices(MotorcycleDao motorcycleDao, VehiclePropertiesServices vehiclePropertiesServices) {
        this.motorcycleDao = motorcycleDao;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

    // --- Public Methods Returning DTOs (for Controllers/Views) ---

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getAllMotorcyclesAsDto() {
        return motorcycleDao.findAllMotorcyclesAsDto();
    }

    @Transactional(readOnly = true)
    public Optional<MotorcycleDto> getMotorcycleByPlateOrTailNumberAsDto(String identifier) {
        return motorcycleDao.findByPlateOrTailNumberAsDto(identifier);
    }

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getMotorcyclesByBrandNameAsDto(String brandName) {
        return motorcycleDao.findByBrandNameAsDto(brandName);
    }

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getMotorcyclesByModelNameAsDto(String modelName) {
        return motorcycleDao.findByModelNameAsDto(modelName);
    }

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getMotorcyclesByModelYearAsDto(Integer modelYear) {
        return motorcycleDao.findByModelYearAsDto(modelYear);
    }

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getMotorcyclesByEngineCCGreaterThanAsDto(Integer cc) {
        return motorcycleDao.findByEngineCCGreaterThanAsDto(cc);
    }

    @Transactional(readOnly = true)
    public List<MotorcycleDto> getMotorcyclesByMobilityTypeAsDto(MotorcycleMobility mobilityType) {
        return motorcycleDao.findByMobilityTypeAsDto(mobilityType);
    }

    // --- Entity-Level Operations (for internal use, updates, deletes) ---

    public Motorcycle getMotorcycleById(Integer id) {
        return motorcycleDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Motorcycle not found with ID: " + id));
    }

    @Transactional
    public Motorcycle saveNewMotorcycle(Motorcycle newMotorcycle) {
        motorcycleDao.findByPlateOrTailNumber(newMotorcycle.getPlateOrTailNumber())
                .ifPresent(m -> {
                    throw new IllegalStateException(
                            "Plate " + newMotorcycle.getPlateOrTailNumber() + " is already taken.");
                });
        return motorcycleDao.save(newMotorcycle);
    }

    @Transactional
    public void deleteMotorcycle(Integer id) {
        if (!motorcycleDao.existsById(id)) {
            throw new IllegalStateException("Motorcycle to delete not found with ID: " + id);
        }
        motorcycleDao.deleteById(id);
    }

    // --- Dynamic Update Methods (unchanged, they need the full entity) ---

    @Transactional
    public Motorcycle updateBrandName(Integer motorcycleId, String newBrandName) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setBrandName(newBrandName);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateModelName(Integer motorcycleId, String newModelName) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setModelName(newModelName);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateModelYear(Integer motorcycleId, Integer newModelYear) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setModelYear(newModelYear);
        return motorcycleDao.save(motorcycle);
    }

   @Transactional
public Motorcycle updatePlateOrTailNumber(Integer motorcycleId, String newIdentifier) {
    if (newIdentifier == null || newIdentifier.trim().isEmpty()) {
        throw new IllegalArgumentException("Identifier cannot be null or empty.");
    }
    Motorcycle motorcycle = getMotorcycleById(motorcycleId);

    if (!motorcycle.getPlateOrTailNumber().equalsIgnoreCase(newIdentifier)
            && motorcycleDao.findByPlateOrTailNumber(newIdentifier).isPresent()) {
        throw new IllegalStateException("Identifier " + newIdentifier + " is already in use by another vehicle.");
    }

    motorcycle.setPlateOrTailNumber(newIdentifier);
    return motorcycleDao.save(motorcycle);
}

    @Transactional
    public Motorcycle updateCurrentFuel(Integer motorcycleId, BigDecimal newCurrentFuel) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setCurrentFuel(newCurrentFuel);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateMaxFuelCapacity(Integer motorcycleId, BigDecimal newMaxCapacity) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setMaxFuelCapacity(newMaxCapacity);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateVehicleValue(Integer motorcycleId, Integer newVehicleValue) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setVehicleValue(newVehicleValue);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateVehicleStatus(Integer motorcycleId, VehicleStatus newStatus) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setVehicleStatus(newStatus);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateKm(Integer motorcycleId, BigDecimal newKm) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setKm(newKm);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateEngineCC(Integer motorcycleId, Integer newEngineCC) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setEngineCC(newEngineCC);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateMobilityType(Integer motorcycleId, MotorcycleMobility newMobilityType) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        motorcycle.setMobilityType(newMobilityType);
        return motorcycleDao.save(motorcycle);
    }

    @Transactional
    public Motorcycle updateDailyPricing(Integer motorcycleId, BigDecimal newDailyPricing) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        vehiclePropertiesServices.updateDailyPricing(motorcycle.getProperties().getPropId(), newDailyPricing);
        return motorcycle;
    }

    @Transactional
    public Motorcycle updateWeeklyPricing(Integer motorcycleId, BigDecimal newWeeklyPricing) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        vehiclePropertiesServices.updateWeeklyPricing(motorcycle.getProperties().getPropId(), newWeeklyPricing);
        return motorcycle;
    }

    @Transactional
    public Motorcycle updateMonthlyPricing(Integer motorcycleId, BigDecimal newMonthlyPricing) {
        Motorcycle motorcycle = getMotorcycleById(motorcycleId);
        vehiclePropertiesServices.updateMonthlyPricing(motorcycle.getProperties().getPropId(), newMonthlyPricing);
        return motorcycle;
    }
}