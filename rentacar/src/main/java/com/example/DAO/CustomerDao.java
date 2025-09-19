package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entities.DbModels.People.Customer;


public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUser_UserId(Integer userId);

    List<Customer> findByCustomerName(String customerName);

    List<Customer> findByCustomerSurname(String customerSurname);

    List<Customer> findByAge(Integer age);

    Optional<Customer> findByCompanyName(String companyName);
}
