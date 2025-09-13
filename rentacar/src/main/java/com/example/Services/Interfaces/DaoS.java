package com.example.Services.Interfaces;

import java.util.List;



public interface DaoS <T>{
    void add(T item);
    T getyId(int id);
    List<T> getAll();
    void update(T item);
    void delete(int id);
}
