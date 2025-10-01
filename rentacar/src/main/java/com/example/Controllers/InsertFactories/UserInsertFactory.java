package com.example.Controllers.InsertFactories;

import com.example.Entities.DbModels.People.User;

import com.example.Services.UserServices;
import com.example.Utils.Global;
import com.example.Utils.HashUtil;
import com.example.Utils.Interfaces.InsertFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UserInsertFactory implements InsertFactory<User, UserServices> {

    private final UserServices userServices;
    private final CustomerInsertFactory customerInsertFactory;

    public UserInsertFactory(UserServices userServices, CustomerInsertFactory customerInsertFactory) {
        this.userServices = userServices;
        this.customerInsertFactory = customerInsertFactory;
    }

    @Override
    public UserServices getSavingService() {
        return this.userServices;
    }

    @Override
    public String getSaveMethodName(Class<User> entityType) {
        // User nesnesini doğrudan alan save metodunu kullanacağız.
        return "-";
    }

    @Override
    public List<String> getFieldsToSkip() {
        return Arrays.asList("userId", "passwd", "role");
    }

    @Override
    public Map<String, Object> collectCustomFieldData() throws Exception {
        Map<String, Object> customData = new HashMap<>();

        System.out.print("Enter Password: ");
        String password = Global.scanner.nextLine();
        System.out.print("Confirm Password: ");
        String passwordConfirm = Global.scanner.nextLine();

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        // Şifreyi hash'le ve 'passwd' anahtarıyla map'e koy.
        byte[] hashedPassword = HashUtil.sha256(password);
        customData.put("passwd", hashedPassword);

        return customData;
    }

    public void start() {
        User createdUser = runDynamicInsertMenu(User.class);
        
        customerInsertFactory.start(createdUser);

       
    }
}