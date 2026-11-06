package com.example.demo.repository;

import java.util.List;

public interface CrudRepository<T, ID> {
    T save(T entity);
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);
}
