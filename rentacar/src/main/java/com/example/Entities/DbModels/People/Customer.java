package com.example.Entities.DbModels.People;

public class Customer {
    private Integer customerId;
    private Integer userId;
    private String customerName;
    private String customerSurname;
    private Integer age;
    private String companyName;
    public Customer(Integer customerId, Integer userId, String customerName, String customerSurname, Integer age,
            String companyName) {
        this.customerId = customerId;
        this.userId = userId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.age = age;
        this.companyName = companyName;
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
