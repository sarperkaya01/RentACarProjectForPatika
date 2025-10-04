package com.example.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.DTO.CustomerInfoDto;
import com.example.DTO.CustomerListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Utils.Enums.UserRoles;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
        Optional<Customer> findByUser_UserId(Integer userId);

        String LIST_DTO_CONSTRUCTOR = "new com.example.DTO.CustomerListDto(" +
                        "c.customerId, c.customerName, c.customerSurname, c.companyName, c.budget, u.email, u.role)";

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u ORDER BY c.customerName")
        List<CustomerListDto> findAllAsListDto();

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE c.customerName = :name")
        List<CustomerListDto> findByCustomerNameAsListDto(@Param("name") String name);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE c.customerSurname = :surname")
        List<CustomerListDto> findByCustomerSurnameAsListDto(@Param("surname") String surname);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE c.age = :age")
        List<CustomerListDto> findByAgeAsListDto(@Param("age") Integer age);

        @Query("SELECT " + LIST_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE u.role = :role")
        List<CustomerListDto> findByRoleAsListDto(@Param("role") UserRoles role);

        String INFO_DTO_CONSTRUCTOR = "new com.example.DTO.CustomerInfoDto(" +
                        "c.customerId, c.customerName, c.customerSurname, c.age, c.companyName, c.budget, u.userId, u.email, u.role)";

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u")
        List<CustomerInfoDto> findAllAsInfoDto();

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE c.customerId = :customerId")
        Optional<CustomerInfoDto> findByIdAsInfoDto(@Param("customerId") Integer customerId);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE u.email = :email")
        Optional<CustomerInfoDto> findByUserEmailAsInfoDto(@Param("email") String email);

        @Query("SELECT " + INFO_DTO_CONSTRUCTOR + " FROM Customer c JOIN c.user u WHERE c.companyName = :companyName")
        Optional<CustomerInfoDto> findByCompanyNameAsInfoDto(@Param("companyName") String companyName);
}
