package com.example.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.CustomerDao;
import com.example.DAO.UserDao;
import com.example.Entities.DbModels.People.Customer;


import jakarta.transaction.Transactional;

public class CustomerServices {

    private final CustomerDao customerDao;
    private final UserDao userDao; // Müşteri eklerken User'a ihtiyacımız olacak.

    @Autowired
    public CustomerServices(CustomerDao customerDao, UserDao userDao) {
        this.customerDao = customerDao;
        this.userDao = userDao;
    }

    @Transactional
    public Customer newCustomer(Customer newCustomer) {
       
        userDao.findById(newCustomer.getUserId())
                .orElseThrow(() -> new IllegalStateException(
                        "Customer can not be created ! " ));

       
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
    public List<Customer> getCustomersByUserId(Integer userId) {
        return customerDao.findByUserId(userId);
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
