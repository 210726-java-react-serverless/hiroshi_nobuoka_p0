package com.revature.p0.respositries;

public abstract class Repository<T> {
    public String name;
    public String route;

    public Repository(String name, String route){
        this.name = name;
        this.route = route;
    }
    abstract void create();
    abstract T search(T type);
    abstract T update(T type);
    abstract void delete(T type);

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }
}
