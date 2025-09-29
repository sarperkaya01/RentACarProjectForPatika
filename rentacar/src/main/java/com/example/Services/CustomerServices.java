package com.example.Services;

import com.example.DAO.CustomerDao;
import com.example.DAO.UserDao;
import com.example.DTO.CustomerInfoDto;
import com.example.DTO.CustomerListDto;
import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Utils.HashUtil;
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
    private final UserServices userServices; // User işlemlerini yönetmek için eklendi

    @Autowired
    public CustomerServices(CustomerDao customerDao, UserDao userDao, UserServices userServices) {
        this.customerDao = customerDao;
        this.userDao = userDao;
        this.userServices = userServices;
    }

    // --- YENİ METOT ---
    // Bu metot, hem kullanıcı hem de müşteri oluşturma işlemini tek bir transaction'da yönetir.
    @Transactional
    public Customer createNewCustomerAndUser(String email, String password, String name, String surname, int age, String companyName) throws Exception {
        // Step 1: User nesnesini oluştur
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPasswd(HashUtil.sha256(password));

        // Rolü belirle
        if (companyName == null || companyName.isBlank()) {
            newUser.setRole(UserRoles.INDIVIDUAL);
        } else {
            newUser.setRole(UserRoles.CORPORATE);
        }

        // User'ı kaydet (Bu aşamada email'in unique olup olmadığı userServices içinde kontrol edilir)
        User savedUser = userServices.saveNewUser(newUser);

        // Step 2: Customer nesnesini oluştur ve User ile ilişkilendir
        Customer newCustomer = new Customer();
        newCustomer.setUser(savedUser);
        newCustomer.setCustomerName(name);
        newCustomer.setCustomerSurname(surname);
        newCustomer.setAge(age);
        newCustomer.setCompanyName(companyName == null || companyName.isBlank() ? "-" : companyName);

        // Customer'ı kaydet (Bu aşamada companyName'in unique olup olmadığı saveNewCustomer içinde kontrol edilir)
        return saveNewCustomer(newCustomer);
    }


    // --- MEVCUT METOTLAR ---

    @Transactional(readOnly = true)
    public Optional<CustomerInfoDto> getCustomersByUserEmailAsInfoDto(String email) {
        return customerDao.findByUserEmailAsInfoDto(email);
    }
    
    // ... (Diğer tüm get, update, delete metotlarınız burada değişmeden kalır) ...
    
    @Transactional(readOnly = true)
    public List<CustomerListDto> getAllCustomersAsListDto() {
        return customerDao.findAllAsListDto();
    }
    @Transactional(readOnly = true)
    public Optional<CustomerInfoDto> getCustomersByCustomerIdAsInfoDto(Integer customerId) {
        return customerDao.findByIdAsInfoDto(customerId);
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

    @Transactional
    public Customer saveNewCustomer(Customer customer) {
        if (!"-".equals(customer.getCompanyName())) {
            customerDao.findByCompanyNameAsInfoDto(customer.getCompanyName())
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
                && customerDao.findByCompanyNameAsInfoDto(newCompanyName).isPresent()) {
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