package com.example.Controllers;


import java.util.Arrays;
import java.util.List;


import com.example.Services.Interfaces.Controller;

public class MainMenuController implements Controller{

   

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void exit() {
       System.exit(0);
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("Rent", "Profile", "Setting", "Exit");
    }

}
