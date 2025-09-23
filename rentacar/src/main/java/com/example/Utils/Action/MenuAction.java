package com.example.Utils.Action;

public class MenuAction {
     private final String displayName;
    private final Runnable action;

    public MenuAction(String displayName, Runnable action) {
        this.displayName = displayName;
        this.action = action;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Runnable getAction() {
        return action;
    }

}
