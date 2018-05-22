package DAO;

import java.io.Serializable;
import java.util.List;

public interface InterfaceDao<T, Id extends Serializable>{
    void create(T entity);
    void update(T entity);
    void delete(T entity);
    void deleteAll();
    T findById(Id id);
    List<T> findAll();
}
