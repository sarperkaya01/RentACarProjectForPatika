package com.example.Controllers.SelectFactories;

import com.example.DTO.MotorcycleInfoDto;
import com.example.Entities.DbModels.Vehicles.Motorcycle;
import com.example.Services.MotorcycleServices;
import com.example.Utils.Interfaces.SelectFactory;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleSelectFactory implements SelectFactory<Motorcycle, MotorcycleInfoDto, MotorcycleServices> {

    private final MotorcycleServices motorcycleServices;

    public MotorcycleSelectFactory(MotorcycleServices motorcycleServices) {
        this.motorcycleServices = motorcycleServices;
    }

    @Override
    public MotorcycleServices getSelectService() {
        return this.motorcycleServices;
    }

    public void start() {
        runSelectMenu(Motorcycle.class);
    }
}
