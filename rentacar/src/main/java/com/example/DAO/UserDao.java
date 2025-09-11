package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.example.Entities.DbModels.People.User;


public class UserDao implements DaoInterface<User> {
    private Connection conn;
    

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void add(User item) {
         String sql = """
                insert into users ( email,passwd)
                values ( ?, ?);                               
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,item.getEmail());
            ps.setString(2,item.getPasswd());
            
       } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getyId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getyId'");
    }

    @Override
    public List<User> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public void update(User item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteVehicle(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteVehicle'");
    }


}
