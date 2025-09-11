package com.example.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.Entities.DbModels.People.User;

public class Global {
    public static final String URL = "jdbc:postgresql://34.134.112.5:5433/rentacar_db";
                                                                                 // erisim yetkisi ver
    public static User activeUser;
    public static Scanner scanner = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "cohort11a", "rentacar");
    }

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
