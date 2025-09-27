package com.example.Services;

import com.example.DAO.CustomerDao;
import com.example.DAO.UserDao;
import com.example.DTO.CustomerInfoDto;
import com.example.DTO.CustomerListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Utils.Enums.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {

    private final CustomerDao customerDao;
    private final UserDao userDao;

    @Autowired
    public CustomerServices(CustomerDao customerDao, UserDao userDao) {
        this.customerDao = customerDao;
        this.userDao = userDao;
    }

    // --- DTO DÖNDÜREN METOTLAR ---

    @Transactional(readOnly = true)
    public List<CustomerListDto> getAllCustomersAsListDto() {
        return customerDao.findAllAsListDto();
    }

    @Transactional(readOnly = true)
    public Optional<CustomerInfoDto> getCustomerByUserEmailAsInfoDto(String email) {
        return customerDao.findByUserEmailAsInfoDto(email);
        
    }

    // --- SELECTFACTORY İÇİN GEREKLİ TÜM METOTLAR ---
    // İsimler, SelectFactory kuralına tam uyumlu:
    // getCustomersBy[FieldName]AsListDto

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByCustomerNameAsDto(String name) { // İSİM DEĞİŞTİ
        return customerDao.findByCustomerNameAsListDto(name);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByCustomerSurnameAsDto(String surname) { // İSİM DEĞİŞTİ
        return customerDao.findByCustomerSurnameAsListDto(surname);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByCompanyNameAsDto(String companyName) { // İSİM DEĞİŞTİ
        return customerDao.findByCompanyNameAsListDto(companyName);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByAgeAsDto(Integer age) { // İSİM DEĞİŞTİ
        return customerDao.findByAgeAsListDto(age);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByEmailAsDto(String email) { // İSİM DEĞİŞTİ
        return customerDao.findByEmailAsListDto(email);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByRoleAsDto(UserRoles role) { // İSİM DEĞİŞTİ
        return customerDao.findByRoleAsListDto(role);
    }

    // Additional fields için
    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByEmailAsListDto(String email) {
        return customerDao.findByEmailAsListDto(email);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByRoleAsListDto(UserRoles role) {
        return customerDao.findByRoleAsListDto(role);
    }

    // --- ENTITY VE UPDATE METOTLARI ---

    public Customer getCustomerById(Integer id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Customer not found with ID: " + id));
    }

    @Transactional
    public Customer saveNewCustomer(Customer customer) {
        if (!"-".equals(customer.getCompanyName())) {
            customerDao.findByCompanyName(customer.getCompanyName())
                    .ifPresent(c -> {
                        throw new IllegalStateException(
                                "Company with name " + customer.getCompanyName() + " already exists.");
                    });
        }
        return customerDao.save(customer);
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        if (!customerDao.existsById(id)) {
            throw new IllegalStateException("Customer to delete not found with ID: " + id);
        }
        customerDao.deleteById(id);
    }

    @Transactional
    public Customer updateCustomerName(Integer customerId, String newName) {
        Customer customer = getCustomerById(customerId);
        customer.setCustomerName(newName);
        return customerDao.save(customer);
    }

    @Transactional
    public Customer updateCustomerSurname(Integer customerId, String newSurname) {
        Customer customer = getCustomerById(customerId);
        customer.setCustomerSurname(newSurname);
        return customerDao.save(customer);
    }

    @Transactional
    public Customer updateAge(Integer customerId, Integer newAge) {
        Customer customer = getCustomerById(customerId);
        customer.setAge(newAge);
        return customerDao.save(customer);
    }

    @Transactional
    public Customer updateCompanyName(Integer customerId, String newCompanyName) {
        Customer customer = getCustomerById(customerId);
        if (!customer.getCompanyName().equalsIgnoreCase(newCompanyName)
                && customerDao.findByCompanyName(newCompanyName).isPresent()) {
            throw new IllegalStateException("Company name " + newCompanyName + " is already in use.");
        }
        customer.setCompanyName(newCompanyName);
        return customerDao.save(customer);
    }

    @Transactional
    public Customer updateEmail(Integer customerId, String newEmail) {
        Customer customer = getCustomerById(customerId);
        User user = customer.getUser();
        if (!user.getEmail().equalsIgnoreCase(newEmail) && userDao.findByEmail(newEmail).isPresent()) {
            throw new IllegalStateException("Email " + newEmail + " is already in use.");
        }
        user.setEmail(newEmail);
        userDao.save(user);
        return customer;
    }

    @Transactional
    public Customer updateRole(Integer customerId, UserRoles newRole) {
        Customer customer = getCustomerById(customerId);
        User user = customer.getUser();
        user.setRole(newRole);
        userDao.save(user);
        return customer;
    }
}