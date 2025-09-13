package com.example.Controllers;

import java.sql.Connection;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.User;
import com.example.Services.Global;
import com.example.Services.HashUtil;
import com.example.Services.Enums.UserRoles;
import com.example.Services.Interfaces.Controller;

public class RegisterController implements Controller {
     private Connection conn;
    private Scanner sc;

    public RegisterController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    

    @Override
    public void start() {
        pageTitle("Register");
        printMenu();

        List<Runnable> actions = Arrays.asList(
                this::addUser,
                this::exit);

        int choice = Global.scanner.nextInt();
        Global.scanner.nextLine();
        menuCases(choice, actions);
    }

    @Override
    public void exit() {
        MainController mc = new MainController(conn,sc);
        mc.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("New User", "Exit");
    }
    public User createNewUserObj(){
        
        System.out.println("Email:");
        String email = Global.scanner.nextLine();
        String passwd;
        String passwd2;
        do {
            // Kullanıcıdan şifre al
            System.out.println("Password:");
            passwd = Global.scanner.nextLine();

            System.out.println("Password again:");
            passwd2 = Global.scanner.nextLine();

            if (!passwd.equals(passwd2)) {
                System.out.println("Passwords do not match. Try again.");
            }

        } while (!passwd.equals(passwd2));

        System.out.println("Company Name (If you haven't, please pass with 'Enter'):");
        String company = Global.scanner.nextLine().trim();

        if (company.isEmpty()) {
            company = "-";
        }
        UserRoles role = company.equals("-") ? UserRoles.INDIVIDUAL : UserRoles.CORPORATE;
        

        return new User(null, email, HashUtil.sha256(passwd2), role);

    };

    private void addUser() {
       User u = createNewUserObj();

       UserDao ud = new UserDao(Global.getConnection());
       ud.getyByEmail(u.getEmail());
       // if condition ile email zaten var yok yoklamasi
       ud.add(u);
       //customer de ekleneck
       System.out.println(u.toString());


       exit();


    }

}
