package com.example.Controllers;


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Utils.Interfaces.Controller;
@Component
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
