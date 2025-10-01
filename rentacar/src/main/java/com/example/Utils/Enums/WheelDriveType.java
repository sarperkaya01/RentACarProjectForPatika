package com.example.Utils.Enums;

public enum WheelDriveType {
    FWD("Front-Wheel Drive", "Power is delivered to the front wheels only."),
    RWD("Rear-Wheel Drive", "Power is delivered to the rear wheels only."),
    AWD("All-Wheel Drive", "Power is distributed to all four wheels automatically.");
    

    private final String fullName;
    private final String description;

    WheelDriveType(String fullName, String description) {
        this.fullName = fullName;
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return fullName + " (" + description + ")";
    }
}
