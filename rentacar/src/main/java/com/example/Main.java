package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.Controllers.MainController;
import com.example.Services.Global;


@SpringBootApplication
public class Main implements CommandLineRunner {

    private final MainController mainController;

   
    // kendisi oluşturup buraya hazır bir şekilde verir.
    @Autowired
    public Main(MainController mainController) {
        this.mainController = mainController;
    }

    public static void main(String[] args) {
       
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Global.printMenuHeader("--- Welcome my rent a car app ---"); // Global.printMenuHeader yerine basit bir S.o.p
        
      
        mainController.start();
    }
}