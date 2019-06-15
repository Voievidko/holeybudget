package com.yourfounds.dao;

import java.util.List;

public interface CrudDao<T> {

    //todo: Option
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(int id);

}