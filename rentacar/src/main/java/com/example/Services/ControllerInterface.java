package com.example.Services;

import java.util.List;


public interface ControllerInterface {
     void start();
    
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
    
    default void menuCases(int choice, List<Runnable> actions) {
        if (choice < 1 || choice > actions.size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }
        actions.get(choice - 1).run();
    }
    
}
