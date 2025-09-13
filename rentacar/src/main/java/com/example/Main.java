package com.example;


import com.example.Controllers.MainController;
import com.example.Services.Global;

public class Main {
    public static void main(String[] args) {
        Global.printMenuHeader("Welcome my rent a car app");

        MainController mc = new MainController(Global.getConnection(),Global.scanner);
        mc.start();

       
        
        
    }
}