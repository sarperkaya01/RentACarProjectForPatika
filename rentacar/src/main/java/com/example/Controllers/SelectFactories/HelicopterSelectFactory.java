package com.example.Controllers.SelectFactories;

import com.example.DTO.HelicopterDto;
import com.example.Entities.DbModels.Vehicles.Helicopter;
import com.example.Services.HelicopterServices;
import com.example.Utils.Interfaces.SelectFactory;
import org.springframework.stereotype.Component;

@Component
public class HelicopterSelectFactory implements SelectFactory<Helicopter, HelicopterDto, HelicopterServices> {

    private final HelicopterServices helicopterServices;

    public HelicopterSelectFactory(HelicopterServices helicopterServices) {
        this.helicopterServices = helicopterServices;
    }

    @Override
    public HelicopterServices getSelectService() {
        return this.helicopterServices;
    }

    public void start() {
        runSelectMenu(Helicopter.class);
    }
}
