package com.example.Services;

import com.example.DAO.VehicleDao;
import com.example.Entities.DbModels.Vehicles.Automobile;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Entities.DbModels.Vehicles.VehiclePricing;
import com.example.Utils.Enums.VehicleTypes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleRegistrationService {

    private final VehicleDao vehicleDao; // Alt türleri de kaydedebilir
    private final VehiclePricingServices vehiclePricingServices;

    public VehicleRegistrationService(VehicleDao vehicleDao, VehiclePricingServices vehiclePricingServices) {
        this.vehicleDao = vehicleDao;
        this.vehiclePricingServices = vehiclePricingServices;
    }

    /**
     * Yeni bir Otomobil kaydını tek bir transaction altında yönetir.
     * 
     * @param automobile Kaydedilmemiş, bilgileri doldurulmuş Automobile nesnesi.
     * @param pricingId  Bu araca bağlanacak olan VehiclePricing profilinin ID'si.
     * @return Veritabanına kaydedilmiş, ID'si atanmış Automobile nesnesi.
     */
    @Transactional
    public Automobile registerNewAutomobile(Automobile automobile) {
        // 1. Seçilen fiyatlandırma profilini veritabanından bul.
        VehiclePricing pricingProfile = vehiclePricingServices.findBySpeciality(automobile.getWheelDriveType().name());

        // 2. Fiyatlandırma profilini, kaydedilecek olan araç nesnesine bağla.
        automobile.setPricing(pricingProfile);
        automobile.setVehicleType(VehicleTypes.AUTOMOBILE);

        // 3. Hazırlanmış nesneyi kaydet. JPA, kalıtım sayesinde doğru tablolara
        // (hem vehicle hem automobile) kayıt yapacaktır.
        // Hata olursa, tüm işlem geri alınır.
        return vehicleDao.save(automobile);
    }

    /**
     * Yeni bir Motosiklet kaydını tek bir transaction altında yönetir.
     * 
     * @param motorcycle Kaydedilmemiş, bilgileri doldurulmuş Motorcycle nesnesi.
     * @param pricingId  Bu araca bağlanacak olan VehiclePricing profilinin ID'si.
     * @return Veritabanına kaydedilmiş, ID'si atanmış Motorcycle nesnesi.
     */
    @Transactional
    public Motorcycle registerNewMotorcycle(Motorcycle motorcycle) {
        VehiclePricing pricingProfile = vehiclePricingServices.findBySpeciality(motorcycle.getMobilityType().name());
        motorcycle.setPricing(pricingProfile);
        motorcycle.setVehicleType(VehicleTypes.MOTORCYCLE);
        return vehicleDao.save(motorcycle);
    }

    @Transactional
    public Helicopter registerNewHelicopter(Helicopter helicopter) {

        VehiclePricing pricingProfile = vehiclePricingServices.findBySpeciality(helicopter.getSpeciality().name());

        helicopter.setPricing(pricingProfile);
        helicopter.setVehicleType(VehicleTypes.HELICOPTER);

        return vehicleDao.save(helicopter);
    }
}