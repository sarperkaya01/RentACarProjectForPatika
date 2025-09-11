package com.example.DAO;

import java.util.List;



public interface DaoInterface <T>{
    void add(T item);
    T getyId(int id);
    List<T> getAll();
    void update(T item);
    void delete(int id);
}
