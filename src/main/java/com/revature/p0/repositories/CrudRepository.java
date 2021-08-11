package com.revature.p0.repositories;

public interface CrudRepository<T> {


    void save(T newResource);
    void update(T updatedResource);
    void delete(String string);

}
