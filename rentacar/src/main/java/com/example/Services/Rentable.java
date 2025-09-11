package com.example.Services;

public interface Rentable {
    double calculateRentalPrice(int days);
    boolean isAvailable();
    void rent();
    void returnVehicle();
    
}
