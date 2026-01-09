package com.votreprojet.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T, K> {
    Optional<T> find(K id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
