package com.example.Controllers;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.example.Services.Interfaces.Controller;

public class MainMenuController implements Controller{

     private Connection conn;
    private Scanner sc;

    public MainMenuController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void exit() {
        MainController mc = new MainController(conn, sc);
        mc.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Rent", "Profile", "Setting", "Exit");
    }

}
