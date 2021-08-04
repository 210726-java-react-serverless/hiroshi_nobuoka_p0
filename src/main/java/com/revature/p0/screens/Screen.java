package com.revature.p0.screens;

import com.revature.p0.respositries.Repository;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Screen {

    protected String name;
    protected String route;

    public Screen(String name, String route){
        this.name = name;
        this.route= route;
    }

    public abstract void render() throws IOException;

}
