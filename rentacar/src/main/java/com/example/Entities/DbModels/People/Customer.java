package com.example.Entities.DbModels.People;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "customer_name", nullable = false, length = 20)
    private String customerName;

    @Column(name = "customer_surname", nullable = false, length = 30)
    private String customerSurname;
    
    @Column(name = "customer_age", nullable = false)
    private Integer age;
    
    @Column(name = "company_name", nullable = false, unique = true, length = 20)
    private String companyName;
    public Customer() {
        
    }
    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", userId=" + userId + ", customerName=" + customerName
                + ", customerSurname=" + customerSurname + ", age=" + age + ", companyName=" + companyName + "]";
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerSurname() {
        return customerSurname;
    }
    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
}
