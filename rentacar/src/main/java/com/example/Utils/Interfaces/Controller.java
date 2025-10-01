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
            // YENİ DÖNÜŞÜM METODUNU ÇAĞIRIYORUZ
            String methodName = toCamelCase(title);

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

    private String toCamelCase(String title) {
        String[] parts = title.split("\\s+"); // Başlığı boşluklardan ayır
        StringBuilder camelCaseString = new StringBuilder(parts[0].toLowerCase()); // İlk kelime küçük harfle başlar

        for (int i = 1; i < parts.length; i++) {
            // Sonraki her kelimenin ilk harfini büyük, geri kalanını küçük yap ve
            // birleştir.
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase())
                    .append(parts[i].substring(1).toLowerCase());
        }

        return camelCaseString.toString();
    }

    default void runMenuLoop(String menuTitle) {
        if (getMenuTitles() == null || getMenuTitles().isEmpty()) {
            return;
        }
        boolean continueLoop = true;
        while (continueLoop) {
            pageTitle(menuTitle);

            List<Runnable> actions = buildActionsFromMenuTitles();

            System.out.print("Please enter your choice: ");
            int choice = -1;
            try {

                String input = Global.scanner.nextLine();

                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {

                System.out.println("Invalid input! Please enter a number.");
            }

            if (choice == actions.size()) {
                continueLoop = false;
            }

            if (choice > 0 && choice <= actions.size()) {
                menuCases(choice, actions);
            } else if (continueLoop) {

                if (choice != -1) {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}