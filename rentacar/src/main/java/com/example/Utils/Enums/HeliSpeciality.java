package com.example.Utils.Enums;

public enum HeliSpeciality {
   Emergency(
        "Medical Helicopter",
        "Air ambulance used for firefighting operations, emergency medical services and patient transport.",
        "Fire / Healthcare"
    ),
    CIVIL(
        "Civil Helicopter",
        "General-purpose civilian helicopters used for transport, tourism, or private use.",
        "Transportation / Tourism / Private"
    ),
    DRONE(
        "Unmanned Aerial Vehicle (Drone)",
        "Remotely piloted or autonomous helicopters used for surveillance, delivery, or research.",
        "Filming / Civil / Research"
    );

    private final String fullName;
    private final String description;
    private final String usage;

    HeliSpeciality(String fullName, String description, String usage) {
        this.fullName = fullName;
        this.description = description;
        this.usage = usage;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public String toString() {
        return fullName + " - " + description + " (Usage: " + usage + ")";
    }
}
