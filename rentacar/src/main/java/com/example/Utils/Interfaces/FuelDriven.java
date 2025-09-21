package com.example.Utils.Interfaces;

import java.math.BigDecimal;

public interface FuelDriven {
    BigDecimal getCurrentFuel();
    BigDecimal getMaxFuelCapacity();
    void refuel(BigDecimal amount);

}
