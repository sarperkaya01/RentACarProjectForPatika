package com.example.Utils.Enums;

public enum MotorcycleMobility {
    TRIKE(
        "Trike",
        "Three-wheeled motorcycle with two wheels at the back and one at the front.",
        "Touring / Cruising"
    ),
    REVERSE_TRIKE(
        "Reverse Trike",
        "Three-wheeled motorcycle with two wheels at the front and one at the back.",
        "Sport / Stability Focused"
    ),    
    STANDARD(
        "Standard",
        "Two-wheeled traditional motorcycle type.",
        "General Purpose"
    );

    private final String fullName;
    private final String description;
    private final String category;

    MotorcycleMobility(String fullName, String description, String category) {
        this.fullName = fullName;
        this.description = description;
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return fullName + " (" + category + ") - " + description;
    }
}
