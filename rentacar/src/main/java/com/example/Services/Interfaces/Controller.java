package com.example.Services.Interfaces;

import java.util.List;

import com.example.Services.Global;


public interface Controller {
     void start();
     void exit();
    
    List<String> getMenuTitles();// menü başlıklarını döndüren metod
    //void exitPage();

   
    default void pageTitle(String title){
        
        Global.printMenuHeader(title);   
        getMenuTitles();
        
    };

    default void printMenu() {
        List<String> titles = getMenuTitles();
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + " - " + titles.get(i));
        }
    }
    
    default boolean menuCases(int choice, List<Runnable> actions) {
        if (choice < 1 || choice > actions.size()) {
            System.out.println("Incorrect choice! Please select a menu again.");
            return false;
        }
        actions.get(choice - 1).run();
        return true;
    }
    // default void checkCaseSelection(List<Runnable> action,int choice){
    //     boolean checkInput = menuCases(choice, action);
        
    //     do {
    //         System.out.println("Incorrect choice! Please select a menu again.");
    //     } while (checkInput==true);
        
    // }
    
}
