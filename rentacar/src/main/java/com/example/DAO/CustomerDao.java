package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.Entities.DbModels.People.Customer;
import com.example.Entities.DbModels.People.User;
import com.example.Services.Abstracts.GenericDaoImp;

public class CustomerDao extends GenericDaoImp<Customer> {
    private Connection conn;

    public CustomerDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean add(Customer item) {

        String sql = """
                INSERT INTO customers(
                customer_id, customer_name, customer_surname, customer_age, company_name)
                VALUES ( ?, ?, ?, ?, ?);
                               """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // buraya bir de customer_id gelecek
            ps.setString(2, item.getCustomerName());
            ps.setString(3, item.getCustomerSurname());
            ps.setInt(4, item.getAge());
            ps.setString(5, item.getCompanyName());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            // System.out.println("New customer added correctly !");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // PostgreSQL UNIQUE violation
                System.out.println("ERROR : This company name is use for another customer already !");
            } else {
                System.out.println("ERROR : Customer can not be created !");
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean add(Customer item, User u) {
        String sql = """
                INSERT INTO customers(
                customer_id, customer_name, customer_surname, customer_age, company_name)
                VALUES ( ?, ?, ?, ?, ?);
                               """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, u.getUserId());
            ps.setString(2, item.getCustomerName());
            ps.setString(3, item.getCustomerSurname());
            ps.setInt(4, item.getAge());
            ps.setString(5, item.getCompanyName());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            // System.out.println("New customer added correctly !");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // PostgreSQL UNIQUE violation
                System.out.println("ERROR : This company name is use for another customer already !");
            } else {
                System.out.println("ERROR : Customer can not be created !");
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void update(Customer item) {
        String sql = """
                UPDATE customers set customer_name=?, customer_surname=?, customer_age=?, company_name=? where customer_id = ?;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getCustomerName());
            ps.setString(2, item.getCustomerSurname());
            ps.setInt(3, item.getAge());
            ps.setString(4, item.getCompanyName());
            ps.setInt(5, item.getCustomerId());

            ps.executeUpdate();
            System.out.println("Customer updated !");
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving from the user database : " + e.getMessage());
            e.printStackTrace();

        }
    }

    @Override
    protected Customer mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getInt("user_id"),
                rs.getString("customer_name"),
                rs.getString("customer_surname"),
                rs.getInt("customer_age"),
                rs.getString("company_name"));
    }

}
