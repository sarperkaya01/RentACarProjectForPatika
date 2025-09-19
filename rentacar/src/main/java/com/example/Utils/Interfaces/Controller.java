package com.example.Utils.Interfaces;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.Utils.Global;

public interface Controller {
    void start();

    void exit();

    List<String> getMenuTitles();// menü başlıklarını döndüren metod
    // void exitPage();

    default void pageTitle(String title) {
        Global.printMenuHeader(title);
        printMenu(); // DÜZELTME: getMenuTitles() yerine printMenu() çağrılmalı.
    }

    default void printMenu() {
        // Bu metot zaten doğru. Listeyi alıp ekrana basıyor.
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

    default List<Runnable> buildActionsFromMenuTitles() {
        List<String> titles = getMenuTitles();
        List<Runnable> actions = new ArrayList<>();

        for (String title : titles) {
            // DÜZELTME: Aradaki boşlukları ve diğer beyaz karakterleri silmek için
            // .replaceAll("\\s+", "") eklendi.
            String methodName = title.toLowerCase().replaceAll("\\s+", "");

            try {
                Method method = this.getClass().getDeclaredMethod(methodName);
                method.setAccessible(true);
                actions.add(() -> {
                    try {
                        method.invoke(this);
                    } catch (Exception e) {
                        System.out.println("Error executing action: " + methodName);
                        e.printStackTrace();
                    }
                });
            } catch (NoSuchMethodException e) {
                System.out.println("Warning: No method found for menu title '" + title + "'. " +
                        "Please create a '" + methodName + "()' method.");
                actions.add(() -> System.out.println("Action for '" + title + "' is not implemented."));
            }
        }
        return actions;
    }

    default void runMenuLoop(String menuTitle) {
         if (getMenuTitles() == null || getMenuTitles().isEmpty()) {
        return;
    }
        boolean continueLoop = true;
        while (continueLoop) {
            pageTitle(menuTitle); // Bu artık printMenu() metodunu da çağırıyor.

            List<Runnable> actions = buildActionsFromMenuTitles();

            System.out.print("Please enter your choice: ");
            int choice = Global.scanner.nextInt();
            Global.scanner.nextLine();

            // Eğer seçim, listenin son elemanı olan 'Exit' ise, döngüyü kır.
            if (choice == actions.size()) {
                continueLoop = false;
            }

            // Seçilen aksiyonu çalıştır.
            // Bu, 'exit()' metodunu da içerir, böylece "Exiting..." mesajı yazdırılır.
            menuCases(choice, actions);
        }
    }

}
