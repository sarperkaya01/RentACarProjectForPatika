package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DAO.CustomerDao;
import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;

import jakarta.transaction.Transactional;

@Service
public class CustomerServices {

    private final CustomerDao customerDao;
    private final UserDao userDao;

    @Autowired
    public CustomerServices(CustomerDao customerDao, UserDao userDao) {
        this.customerDao = customerDao;
        this.userDao = userDao;
    }

   @Transactional
    public Customer newCustomer(Customer newCustomer) {
        // DÜZELTME: Customer'a bağlı User nesnesi ve onun ID'si kontrol edilmeli.
        User user = newCustomer.getUser();
        if (user == null || user.getUserId() == null || !userDao.existsById(user.getUserId())) {
            throw new IllegalStateException("Müşteri oluşturulamaz! Geçerli bir kullanıcıya bağlanmalıdır.");
        }
        
        // Bir kullanıcının zaten bir müşteri profili var mı diye kontrol etmek iyi bir fikir.
        if(customerDao.findByUser_UserId(user.getUserId()).isPresent()){
            throw new IllegalStateException("Bu kullanıcı için zaten bir müşteri profili mevcut.");
        }

        return customerDao.save(newCustomer);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Customer not found ! "));
    }

    @Transactional
    public Customer updateCustomer(Customer customerToUpdate) {
        if (customerToUpdate.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer id null !");
        }

        customerDao.findById(customerToUpdate.getCustomerId())
                .orElseThrow(() -> new IllegalStateException(
                        "Customer not updated ! "));

        return customerDao.save(customerToUpdate);
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        if (!customerDao.existsById(id)) {
            throw new IllegalStateException("Silinecek müşteri bulunamadı: " + id);
        }
        customerDao.deleteById(id);
    }

    public Optional<Customer> getCustomerByUserId(Integer userId) {
        return customerDao.findByUser_UserId(userId);
    }

    public List<Customer> getCustomersByName(String name) {
        return customerDao.findByCustomerName(name);
    }

    public List<Customer> getCustomersBySurname(String surname) {
        return customerDao.findByCustomerSurname(surname);
    }

    public List<Customer> getCustomersByAge(Integer age) {
        return customerDao.findByAge(age);
    }

    public Customer getCustomerByCompanyName(String companyName) {
        return customerDao.findByCompanyName(companyName)
                .orElseThrow(() -> new IllegalStateException(
                        "There is no such customer : " + companyName));
    }

}
