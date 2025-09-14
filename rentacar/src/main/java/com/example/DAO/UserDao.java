package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.example.Entities.DbModels.People.User;
import com.example.Services.Abstracts.GenericDaoImp;
import com.example.Services.Enums.UserRoles;

public class UserDao extends GenericDaoImp<User> {
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean add(User item) {
        String sql = """
                insert into users ( email,passwd,user_role)
                values ( ?, ?,?); -- ::user_rolesenum cast for postgresql safety
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getEmail());
            ps.setBytes(2, item.getPasswd());
            ps.setString(3, item.getRole().name());
            int affectedRows = ps.executeUpdate();

            // Eğer en az bir satır etkilendiyse, işlem başarılıdır.
            return affectedRows > 0;
            // System.out.println("New user added correctly !");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // PostgreSQL UNIQUE violation
                System.out.println("ERROR : This email already taken !");
                
            } else {
                System.out.println("ERROR : User can not be created !");
                e.printStackTrace();
            }
            return false;
        }
    }

    public User getyByEmail(String email) {
        String sql = """
                select * from  USERS WHERE email= ?;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getBytes("passwd"),
                        UserRoles.valueOf(rs.getString("user_role")));
                return u;

            } else {
                System.out.println("There isn't any user for this id : " + email);
                return null;
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving from the user database : " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void update(User item) {
        String sql = """
                UPDATE users set email=?,passwd=?,user_role=? where user_id = ?;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getEmail());
            ps.setBytes(2, item.getPasswd());
            ps.setString(3, item.getRole().name());
            ps.setInt(4, item.getUserId());

            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving from the user database : " + e.getMessage());
            e.printStackTrace();

        }
    }

    @Override
    public void delete(int id) {
        String sql = """
                DELETE FROM users where user_id = ?;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("User deleted !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getBytes("passwd"),
                UserRoles.valueOf(rs.getString("user_role")));
    }

}
