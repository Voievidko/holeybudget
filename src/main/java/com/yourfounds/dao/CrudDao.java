package com.yourfounds.dao;

import java.util.List;

public interface CrudDao<T,E> {

    //todo: Option
    T get(E id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(E id);

}