package com.example.Controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.Controllers.InsertFactories.CustomerInsertFactory;

import com.example.Utils.Interfaces.Controller;

@Component
public class RegisterController implements Controller {
    private final CustomerInsertFactory customerInsertFactory;
    private final MainController mainController;

    @Autowired
    public RegisterController(CustomerInsertFactory customerInsertFactory, @Lazy MainController mainController) {
        this.customerInsertFactory = customerInsertFactory;
        this.mainController = mainController;
    }

    @Override
    public void start() {
        runMenuLoop("Register");
    }

    @Override
    public void exit() {

        mainController.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Create New Account", "Back to Main Menu");
    }

    @SuppressWarnings("unused")
    private void createNewAccount() {

        customerInsertFactory.start();

        System.out.println("\nReturning to the main menu...");
        exit();
    }

}
