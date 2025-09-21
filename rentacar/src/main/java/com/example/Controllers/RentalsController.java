package com.example.Controllers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Utils.Interfaces.Controller;
@Component
public class RentalsController implements Controller {

    @Override
    public void start() {
        runMenuLoop("Rentals Menu");
    }

    @Override
    public void exit() {
        System.out.println("");
    }

    @Override
    public List<String> getMenuTitles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMenuTitles'");
    }

}
