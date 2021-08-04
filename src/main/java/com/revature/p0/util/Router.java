package com.revature.p0.util;

public interface Router<T> {
    void add(T type);
    void changeCurrent(String string);
    T getCurrent();
}
