package com.example.Utils.Enums;

public enum VehicleTypes {
    AUTO(true),
    MOTORCYCLE(true),
    HELICOPTER(false); // hava araclarina ozel bir calisma yapilabilir...

    private final boolean isLandVehicle;

    VehicleTypes(boolean isLandVehicle) {
        this.isLandVehicle = isLandVehicle;
    }

    public boolean isLandVehicle() {
        return isLandVehicle;
    }
}
