package com.example.Utils;


import java.util.Scanner;

import com.example.Entities.DbModels.People.User;

public class Global {
    
    //private final Class<T> type; = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    public static Scanner scanner = new Scanner(System.in);
    public static User currentUser = new User();
    //public static final ThreadLocal<User> newlyCreatedUserContext = new ThreadLocal<>();

    public static void printMenuHeader(String title) {
        int width = 40; // Kutu genişliği (╔═════════40══════════╗)
        int paddingSize = (width - title.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        String formattedTitle = padding + title + padding;

        if (formattedTitle.length() < width) {
            formattedTitle += " ";
        }

        System.out.println("╔" + "═".repeat(width) + "╗");
        System.out.println("║" + formattedTitle + "║");
        System.out.println("╚" + "═".repeat(width) + "╝");
    }

}
