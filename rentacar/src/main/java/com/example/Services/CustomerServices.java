package com.example.Services;

import com.example.DAO.CustomerDao;
import com.example.DTO.CustomerInfoDto;
import com.example.DTO.CustomerListDto;
import com.example.Entities.DbModels.People.Customer;

import com.example.Utils.Global;
import com.example.Utils.Enums.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {

    private final CustomerDao customerDao;
    private final UserServices userServices;

    @Autowired
    public CustomerServices(CustomerDao customerDao, UserServices userServices) {
        this.customerDao = customerDao;
        this.userServices = userServices;
    }

    // --- CRUD: CREATE ---

    @Transactional
    public Customer saveNewCustomer(Customer customer) {

        String companyName = customer.getCompanyName();
        String finalCompanyName = (companyName == null || companyName.isBlank()) ? "-" : companyName;
        customer.setCompanyName(finalCompanyName);

        if (!"-".equals(finalCompanyName)) {
            customerDao.findByCompanyNameAsInfoDto(finalCompanyName).ifPresent(c -> {
                throw new IllegalStateException("Company with name " + finalCompanyName + " already exists.");
            });
        }
        // customer.setUser(Global.newlyCreatedUserContext.get());
        // UserRoles ur = finalCompanyName == "-" ? UserRoles.CORPORATE : UserRoles.INDIVIDUAL;

        // userServices.updateRole(Global.newlyCreatedUserContext.get().getUserId(), ur);

        return customerDao.save(customer);
    }

    // --- CRUD: READ ---

    @Transactional(readOnly = true)
    public List<CustomerListDto> getAllCustomersAsListDto() {
        return customerDao.findAllAsListDto();
    }

    @Transactional(readOnly = true)
    public Optional<CustomerInfoDto> getCustomersByCustomerIdAsInfoDto(Integer customerId) {
        return customerDao.findByIdAsInfoDto(customerId);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerInfoDto> getCustomersByUserEmailAsInfoDto(String email) {
        return customerDao.findByUserEmailAsInfoDto(email);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByCustomerNameAsListDto(String name) {
        return customerDao.findByCustomerNameAsListDto(name);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByCustomerSurnameAsListDto(String surname) {
        return customerDao.findByCustomerSurnameAsListDto(surname);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerInfoDto> getCustomersByCompanyNameAsInfoDto(String companyName) {
        return customerDao.findByCompanyNameAsInfoDto(companyName);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByAgeAsListDto(Integer age) {
        return customerDao.findByAgeAsListDto(age);
    }

    @Transactional(readOnly = true)
    public List<CustomerListDto> getCustomersByRoleAsListDto(UserRoles role) {
        return customerDao.findByRoleAsListDto(role);
    }

    public Customer getCustomerById(Integer id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new IllegalStateException("Customer not found with ID: " + id));
    }

    // --- CRUD: UPDATE ---

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
                && customerDao.findByCompanyNameAsInfoDto(newCompanyName).isPresent()) {
            throw new IllegalStateException("Company name " + newCompanyName + " is already in use.");
        }
        customer.setCompanyName(newCompanyName);
        return customerDao.save(customer);
    }

    @Transactional
    public Customer updateEmail(Integer customerId, String newEmail) {
        Customer customer = getCustomerById(customerId);
        userServices.updateEmail(customer.getUser().getUserId(), newEmail);
        return customer;
    }

    @Transactional
    public Customer updateRole(Integer customerId, UserRoles newRole) {
        Customer customer = getCustomerById(customerId);
        userServices.updateRole(customer.getUser().getUserId(), newRole);
        return customer;
    }

    // --- CRUD: DELETE ---

    @Transactional
    public void deleteCustomer(Integer id) {
        if (!customerDao.existsById(id)) {
            throw new IllegalStateException("Customer to delete not found with ID: " + id);
        }

        customerDao.deleteById(id);
    }
}