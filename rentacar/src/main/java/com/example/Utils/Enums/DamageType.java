package com.example.Utils.Enums;

import java.math.BigDecimal;

public enum DamageType {

    NO_DAMAGE("0.00"),
    MINOR_SCRATCH("150.50"),
    DENT("400.00"),
    WINDSHIELD_CRACK("1250.75"),
    TIRE_REPLACEMENT("2500.00"); 

    private final BigDecimal fee;

    DamageType(String fee) {
        this.fee = new BigDecimal(fee);
    }

    public BigDecimal getFee() {
        return fee;
    }

}
