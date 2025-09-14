package com.example.Services.Interfaces;

import java.util.List;



public interface DaoS <T>{
    boolean add(T item);    
    
    List<T> getAll();
    void update(T item);
    void delete(int id);
	T getById(int id);
    String getTableName();
    String getByIdQuery();
}
