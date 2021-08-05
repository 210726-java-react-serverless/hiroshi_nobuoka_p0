package com.revature.p0.respositries;

public abstract class Repository<T> {
    public String name;
    public String route;

    public Repository(String name, String route){
        this.name = name;
        this.route = route;
    }

    public abstract T search(T type);
    public abstract void save(T type);
    public abstract boolean exists(String thing);

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }
}
