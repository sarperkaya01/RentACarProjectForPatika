package com.example.Controllers;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.Entities.DbModels.People.User;
import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Interfaces.Controller;
@Component
public class RegisterController implements Controller {
    private final UserServices userServices;
    private final MainController mainController;

    @Autowired
    public RegisterController(UserServices userServices, @Lazy MainController mainController) {
        this.userServices = userServices;
        this.mainController = mainController;
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
        mainController.start();
    }

    @Override
    public List<String> getMenuTitles() {
        return Arrays.asList("New User", "Exit");
    }

    private void addUser() {
         try {
            System.out.println("Lütfen email adresinizi girin:");
            String email = Global.scanner.nextLine();

            System.out.println("Lütfen şifrenizi girin:");
            String password = Global.scanner.nextLine();

            
            User newUser = new User();
            newUser.setEmail(email);
            
            newUser.setPasswd(HashUtil.sha256(password)); 
            
            newUser.setRole(UserRoles.INDIVIDUAL); 

            
            User savedUser = userServices.newUser(newUser);

            System.out.println("Kayıt başarılı! Kullanıcı ID: " + savedUser.getUserId());

        } catch (IllegalStateException e) {
            
            System.out.println("Hata: " + e.getMessage());
        } catch (Exception e) {
            
            System.out.println("Beklenmedik bir hata oluştu: " + e.getMessage());
        } finally {
            
            exit();
        }

        

    }
    

}
