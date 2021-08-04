package com.revature.p0.p0.respositries;

public interface CRUD_Repository<T> {

    void create(T type);
    T search(T type);
    T update(T type);
    void delete(T type);


}
