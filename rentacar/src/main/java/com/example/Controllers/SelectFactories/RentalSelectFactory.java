package com.example.Controllers.SelectFactories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.DTO.RentalInfoDto;

import com.example.Entities.Renting.Rental;

import com.example.Services.RentalServices;
import com.example.Utils.Interfaces.SelectFactory;

@Component
public class RentalSelectFactory implements SelectFactory<Rental, RentalInfoDto, RentalServices> {

    private final RentalServices rentalServices;

    @Autowired
    public RentalSelectFactory(RentalServices rentalServices) {
        this.rentalServices = rentalServices;
    }

    @Override
    public RentalServices getSelectService() {
        return this.rentalServices;
    }

    public void start() {
        runSelectMenu(Rental.class);
    }

}
