package com.example.Services.DbTransactions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.DAO.CustomerDao;
import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Services.DoubleTableResult;
import com.example.Services.Global;
import com.example.Services.HashUtil;
import com.example.Services.Enums.UserRoles;

public class RegistrationTransactions {
    private  Connection conn;
    private Scanner sc;
    
    public RegistrationTransactions() {
        this.conn = Global.getConnection();
        this.sc = Global.scanner;
    }

    public boolean registerNewUser() {
        
        try {
            // 1. Transaction için bağlantıyı al
            conn = Global.getConnection();

            // 2. Transaction'ı manuel kontrole al
            conn.setAutoCommit(false);

            // DAO nesnelerini aynı connection ile oluştur
            UserDao userDao = new UserDao(conn);
            CustomerDao customerDao = new CustomerDao(conn);

            // 3. Kullanıcıdan bilgileri al
            
            DoubleTableResult<User, Customer> ucr = createNewUserObj();

            User userToCreate = ucr.getT();
            Customer customerToCreate = ucr.getR();

            // 4. ADIM: User'ı eklemeyi dene

            boolean userAdded = userDao.add(userToCreate);

            if (!userAdded) {
                conn.rollback();
                return false;
            }

            // 5. ADIM: Eklenen kullanıcıyı ID'si ile birlikte geri al
            //
            User createdUser = userDao.getyByEmail(userToCreate.getEmail());

            if (createdUser == null) {
                System.err.println(
                        "Critical error: User created but record about "+userToCreate.getEmail()+" can not find in data base. Transaction rolled back");
                conn.rollback();
                return false;
            }

            // 6. ADIM: Customer'ı eklemeyi dene
            
            boolean customerAdded = customerDao.add(customerToCreate, createdUser);

            if (!customerAdded) {
              
                System.err.println("Customer not created then process stopped");
                conn.rollback();
                return false;
            }

            // 7. Step: COMMIT 
            
            conn.commit();

            System.out.println("-----> Records have been created! <-----");
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //e.printStackTrace();            
            
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println(e.getMessage());
                    //ex.printStackTrace();
                }
            
            return false;
        } finally {
            // İşlem başarılı da olsa, başarısız da olsa bağlantıyı kapat ve auto-commit'i
            // eski haline getir.
          
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        }
    }
     private DoubleTableResult<User, Customer> createNewUserObj() {

        System.out.println("Email:");
        String email = sc.nextLine();
        String passwd;
        String passwd2;
        do {
            // Kullanıcıdan şifre al
            System.out.println("Password:");
            passwd = sc.nextLine();

            System.out.println("Password again:");
            passwd2 = sc.nextLine();

            if (!passwd.equals(passwd2)) {
                System.out.println("Passwords do not match. Try again.");
            }

        } while (!passwd.equals(passwd2));

        Customer c = createNewCustomerObj();
        UserRoles role = c.getCompanyName().equals("-") ? UserRoles.INDIVIDUAL : UserRoles.CORPORATE;
        return new DoubleTableResult<>(
                new User(null, email, HashUtil.sha256(passwd2), role),
                c);

    };

    private Customer createNewCustomerObj() {

        System.out.println("Name:");
        String name = sc.nextLine();

        // Kullanıcıdan şifre al
        System.out.println("Surname:");
        String surname = sc.nextLine();

        System.out.println("Age:");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.println("Company Name (If you haven't, please pass with 'Enter'):");
        String company = sc.nextLine().trim();

        if (company.isEmpty()) {
            company = "-";
        }

        return new Customer(null, null, null, surname, age, company);

    };

}
