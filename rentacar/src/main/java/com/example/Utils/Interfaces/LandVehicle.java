package com.example.Utils.Interfaces;

import java.math.BigDecimal;

public interface LandVehicle {
 String getPlate();
    BigDecimal getKm();
    void addKm(BigDecimal distance);
}
