package com.example.Services;


import com.example.Entities.DbModels.People.User;

public class Global {
    public static final String URL = "jdbc:postgresql://localhost:5432/rentacar";
    public static User activeUser;

    // public static Connection getConnection() throws SQLException {
    //     return DriverManager.getConnection(URL,activeUser.getEmail(),activeUser.getPasswd());
    // }

    public static void printMenuHeader(String title) {
        int width = 40; // Kutu genişliği (╔═════════40══════════╗)
        int paddingSize = (width - title.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        String formattedTitle = padding + title + padding;

        // Genişlik tek sayıda ve title uzunluğu çiftse hizalama kayabilir, düzeltelim
        if (formattedTitle.length() < width) {
            formattedTitle += " ";
        }

        System.out.println("╔" + "═".repeat(width) + "╗");
        System.out.println("║" + formattedTitle + "║");
        System.out.println("╚" + "═".repeat(width) + "╝");
    }

}
