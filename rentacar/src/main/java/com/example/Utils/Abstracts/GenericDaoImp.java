package com.example.Utils.Abstracts;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Services.Global;
import com.example.Services.Interfaces.DaoS;

public abstract class GenericDaoImp<T> implements DaoS<T> {

    protected final Connection conn;
    private final Class<T> type;
    private final String tableName;
    private final String primaryKeyColumnName;

    public GenericDaoImp() {

        this.conn = Global.getConnection();
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        this.tableName = type.getSimpleName().toUpperCase() + "S";
        this.primaryKeyColumnName = type.getSimpleName().toLowerCase() + "_id";
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override

    public String getByIdQuery() {
        return "SELECT * FROM " + getTableName() + " WHERE " + this.primaryKeyColumnName + " = ?";
    }
    
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    @Override
    public T getById(int id) {
        T entity = null;
        try (PreparedStatement ps = conn.prepareStatement(getByIdQuery())) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entity = mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE "
                + getTableName().substring(0, getTableName().length() - 1) + "_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() {
        List<T> entityList = new ArrayList<>();

        String sql = "SELECT * FROM " + getTableName();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                T entity = mapResultSetToEntity(rs);
                entityList.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(getTableName() + " - " + e.getMessage());
            e.printStackTrace();

        }
        return entityList;
    }

}
