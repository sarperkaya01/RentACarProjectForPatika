package com.example.Controllers.InsertFactories;

import com.example.DTO.CustomerInfoDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Services.CustomerServices;
import com.example.Services.UserServices;
import com.example.Utils.Enums.UserRoles;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.Interfaces.InsertFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerInsertFactory implements InsertFactory<Customer, CustomerInfoDto> {

    private final CustomerServices customerServices;
    private final UserServices userServices;

    public CustomerInsertFactory(CustomerServices customerServices, UserServices userServices) {
        this.customerServices = customerServices;
        this.userServices = userServices;
    }

    @Override
    public Optional<CustomerInfoDto> getDtoByIdentifier(String identifier) {
        // Müşterileri email ile bulmak daha mantıklı, bu yüzden email'i identifier olarak kullanacağız.
        return customerServices.getCustomerByUserEmailAsInfoDto(identifier);
    }

    @Override
    public Customer performInsertionProcess() throws Exception {
        // Step 1: User bilgileri (Hesap oluşturma)
        System.out.println("Step 1: Account Creation");
        System.out.print("Enter email address: ");
        String email = Global.scanner.nextLine();

        System.out.print("Enter password: ");
        String password = Global.scanner.nextLine();
        System.out.print("Enter password again to confirm: ");
        String passwordConfirm = Global.scanner.nextLine();
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPasswd(HashUtil.sha256(password));
        // Rol, müşteri bilgileri girildikten sonra belirlenecek.

        // Step 2: Customer bilgileri (Profil oluşturma)
        System.out.println("\nStep 2: Profile Information");
        System.out.print("Enter your name: ");
        String name = Global.scanner.nextLine();
        System.out.print("Enter your surname: ");
        String surname = Global.scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = Integer.parseInt(Global.scanner.nextLine());
        System.out.print("Enter your company name (leave blank if individual): ");
        String companyName = Global.scanner.nextLine();

        // Rolü belirle
        if (companyName.isBlank()) {
            newUser.setRole(UserRoles.INDIVIDUAL);
        } else {
            newUser.setRole(UserRoles.CORPORATE);
        }

        // Önce User'ı kaydet
        User savedUser = userServices.saveNewUser(newUser);

        // Şimdi Customer'ı oluştur ve User'ı bağla
        Customer newCustomer = new Customer();
        newCustomer.setUser(savedUser);
        newCustomer.setCustomerName(name);
        newCustomer.setCustomerSurname(surname);
        newCustomer.setAge(age);
        newCustomer.setCompanyName(companyName.isBlank() ? "-" : companyName);

        // Son olarak Customer'ı kaydet
        return customerServices.saveNewCustomer(newCustomer);
    }
    
    public void start() {
        runInsertMenu();
    }
}