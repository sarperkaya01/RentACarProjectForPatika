package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.DTO.CustomerInfoDto;
import com.example.Entities.DbModels.People.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUser_UserId(Integer userId);

    List<Customer> findByCustomerName(String customerName);

    List<Customer> findByCustomerSurname(String customerSurname);

    List<Customer> findByAge(Integer age);

    Optional<Customer> findByCompanyName(String companyName);

    @Query("SELECT new com.example.DTO.CustomerInfoDto(" +
            "c.customerName, c.customerSurname, c.companyName, u.email, u.role) " +
            "FROM Customer c JOIN c.user u " +
            "ORDER BY c.customerName")
    List<CustomerInfoDto> findAllCustomerInfo();
}
