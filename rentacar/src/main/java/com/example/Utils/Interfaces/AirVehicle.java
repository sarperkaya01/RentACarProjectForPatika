package com.example.Utils.Interfaces;

import java.math.BigDecimal;

public interface AirVehicle {

    String getTailNumber();
    BigDecimal getFlightHours();
    void addFlightHours(BigDecimal hours);

}
