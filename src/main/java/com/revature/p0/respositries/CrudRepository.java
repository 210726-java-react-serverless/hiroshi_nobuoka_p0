package com.revature.p0.respositries;

public interface CrudRepository<T> {

    T findById(String id);
    void save(T newResource);
    boolean update(T updatedResource);
    boolean deleteById(int id);

}
