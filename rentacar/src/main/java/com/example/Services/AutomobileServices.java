package com.example.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.DAO.AutomobileDao;
import com.example.DTO.AutomobileDto;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Utils.Enums.VehicleStatus;
import com.example.Utils.Enums.WheelDriveType;

@Service
public class AutomobileServices {

    private final AutomobileDao automobileDao;
    private final VehiclePropertiesServices vehiclePropertiesServices;

    @Autowired
    public AutomobileServices(AutomobileDao automobileDao, VehiclePropertiesServices vehiclePropertiesServices) {
        this.automobileDao = automobileDao;
        this.vehiclePropertiesServices = vehiclePropertiesServices;
    }

    @Transactional
    public Automobile saveNewAutomobile(Automobile newAutomobile) {
        if (automobileDao.findByPlateOrTailNumber(newAutomobile.getPlateOrTailNumber()).isPresent()) {
            throw new IllegalStateException("Plate " + newAutomobile.getPlateOrTailNumber() + " is already taken.");
        }
        return automobileDao.save(newAutomobile);
    }

    public Automobile getAutomobileById(Integer id) {
        return automobileDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Automobile not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<AutomobileDto> getAutomobileByPlateOrTailNumberAsDto(String plate) {

        return automobileDao.findByPlateOrTailNumberAsDto(plate);
    }

    @Transactional
    public void deleteAutomobile(Integer id) {
        if (!automobileDao.existsById(id)) {
            throw new IllegalStateException("Automobile to delete not found with ID: " + id);
        }
        automobileDao.deleteById(id);
    }

    // @Transactional
    // public Automobile updateAutomobile(Automobile automobileToUpdate) {

    // Automobile existingAutomobile =
    // automobileDao.findById(automobileToUpdate.getId())
    // .orElseThrow(() -> new IllegalStateException(
    // "Automobile to update not found with ID: " + automobileToUpdate.getId()));

    // if
    // (!existingAutomobile.getPlateOrTailNumber().equals(automobileToUpdate.getPlateOrTailNumber()))
    // {
    // if
    // (automobileDao.findByPlateOrTailNumber(automobileToUpdate.getPlateOrTailNumber()).isPresent())
    // {
    // throw new IllegalStateException(
    // "Plate " + automobileToUpdate.getPlateOrTailNumber() + " is already taken by
    // another vehicle.");
    // }
    // }

    // return automobileDao.save(automobileToUpdate);
    // }

    @Transactional
    public Automobile updateBrandName(Integer automobileId, String newBrandName) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setBrandName(newBrandName);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateModelName(Integer automobileId, String newModelName) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setModelName(newModelName);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateModelYear(Integer automobileId, Integer newModelYear) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setModelYear(newModelYear);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updatePlateOrTailNumber(Integer automobileId, String newPlate) {
        if (newPlate == null || newPlate.trim().isEmpty()) {
            throw new IllegalArgumentException("Plaka boş olamaz.");
        }
        Automobile automobile = getAutomobileById(automobileId);

        if (!automobile.getPlateOrTailNumber().equalsIgnoreCase(newPlate)
                && automobileDao.findByPlateOrTailNumber(newPlate).isPresent()) {
            throw new IllegalStateException("Plate " + newPlate + " is already in use.");
        }

        automobile.setPlateOrTailNumber(newPlate);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateKm(Integer automobileId, BigDecimal newKm) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setKm(newKm);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateCurrentFuel(Integer automobileId, BigDecimal newCurrentFuel) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setCurrentFuel(newCurrentFuel);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateVehicleStatus(Integer automobileId, VehicleStatus newStatus) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setVehicleStatus(newStatus);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateWheelDriveType(Integer automobileId, WheelDriveType newWheelDriveType) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setWheelDriveType(newWheelDriveType);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateMaxFuelCapacity(Integer automobileId, BigDecimal newMaxCapacity) {
        Automobile automobile = getAutomobileById(automobileId);
        automobile.setMaxFuelCapacity(newMaxCapacity);
        return automobileDao.save(automobile);
    }

    @Transactional
    public Automobile updateDailyPricing(Integer automobileId, BigDecimal newDailyPricing) {
        Automobile automobile = getAutomobileById(automobileId);
        // İşi asıl uzmanına devret
        vehiclePropertiesServices.updateDailyPricing(automobile.getProperties().getPropId(), newDailyPricing);
        return automobile;
    }

    @Transactional
    public Automobile updateWeeklyPricing(Integer automobileId, BigDecimal newWeeklyPricing) {
        Automobile automobile = getAutomobileById(automobileId);
        vehiclePropertiesServices.updateWeeklyPricing(automobile.getProperties().getPropId(), newWeeklyPricing);
        return automobile;
    }

    @Transactional
    public Automobile updateMonthlyPricing(Integer automobileId, BigDecimal newMonthlyPricing) {
        Automobile automobile = getAutomobileById(automobileId);
        vehiclePropertiesServices.updateMonthlyPricing(automobile.getProperties().getPropId(), newMonthlyPricing);
        return automobile;
    }

    @Transactional(readOnly = true)
    public List<AutomobileDto> getAllAutomobilesAsDto() {
        return automobileDao.findAllAutomobileAsDto();
    }

    @Transactional(readOnly = true)
    public List<AutomobileDto> getAutomobilesByBrandNameAsDto(String brandName) {
        return automobileDao.findByBrandNameAsDto(brandName);
    }

    @Transactional(readOnly = true)
    public List<AutomobileDto> getAutomobilesByModelNameAsDto(String modelName) {
        return automobileDao.findByModelNameAsDto(modelName);
    }

    @Transactional(readOnly = true)
    public List<AutomobileDto> getAutomobilesByModelYearAsDto(Integer modelYear) {
        return automobileDao.findByModelYearAsDto(modelYear);
    }

    @Transactional(readOnly = true)
    public List<AutomobileDto> getAutomobilesByWheelDriveTypeAsDto(WheelDriveType wheelDriveType) {
        return automobileDao.findByWheelDriveTypeAsDto(wheelDriveType);
    }

    // private AutomobileDto convertToDto(Automobile a) {
    // return new AutomobileDto(
    // a.getId(),
    // a.getBrandName(),
    // a.getModelName(),
    // a.getModelYear(),
    // a.getPlateOrTailNumber(),
    // a.getCurrentFuel(),
    // a.getMaxFuelCapacity(),
    // a.getVehicleValue(),
    // a.getVehicleStatus(),
    // a.getProperties().getDailyPricing(),
    // a.getProperties().getWeeklyPricing(),
    // a.getProperties().getMonthlyPricing(),
    // a.getKm(),
    // a.getWheelDriveType());
    // }

}
