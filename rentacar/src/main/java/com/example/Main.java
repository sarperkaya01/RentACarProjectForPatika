package com.example;

import com.example.Controllers.UserController;
import com.example.Services.Global;

public class Main {
    public static void main(String[] args) {
        Global.printMenuHeader("Welcome my rent a car app");

        UserController uc = new UserController(Global.scanner);

        uc.start();
    }
}