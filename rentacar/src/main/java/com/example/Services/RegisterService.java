package com.example.Services;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Utils.Enums.UserRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final UserServices userServices;
    private final CustomerServices customerServices;

    @Autowired
    public RegisterService(UserServices userServices, CustomerServices customerServices) {
        this.userServices = userServices;
        this.customerServices = customerServices;
    }

    @Transactional
    public Customer registerNewCustomer(User user, Customer customer) {
        // --- ADIM 1: User'ı Kaydet ---
        // Bu metot, mevcut transaction'a dahil olacak.
        User savedUser = userServices.saveNewUser(user);

        // --- ADIM 2: Customer Nesnesini Hazırla ---
        // Gerekli bilgileri (kaydedilmiş User gibi) Customer nesnesine ata.
        customer.setUser(savedUser);

        // Şirket adına göre rolü belirle ve User'ı güncelle.
        String companyName = customer.getCompanyName();
        String finalCompanyName = (companyName == null || companyName.isBlank()) ? "-" : companyName;
        customer.setCompanyName(finalCompanyName);

        UserRoles role = "-".equals(finalCompanyName) ? UserRoles.INDIVIDUAL : UserRoles.CORPORATE;
        userServices.updateRole(savedUser.getUserId(), role);

        // --- ADIM 3: Customer'ı Kaydet ---
        // Bu metot da aynı transaction'a dahil olacak.
        // Eğer burada bir hata (örn: duplicate company) olursa, HEM BU ADIM HEM DE 1.
        // ADIM
        // otomatik olarak geri alınacak.
        return customerServices.saveNewCustomer(customer);
    }

}
