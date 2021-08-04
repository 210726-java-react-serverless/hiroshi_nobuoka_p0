package com.revature.p0.p0.respositries;

public abstract class Repository<T> {
    abstract void create(T type);
    abstract T search(T type);
    abstract T update(T type);
    abstract void delete(T type);

}
