package com.revature.p0.screens;

import java.io.IOException;

public abstract class Screen {

    protected String name;
    protected String route;

    public Screen(String name, String route){
        this.name = name;
        this.route= route;
    }

    public abstract void render() throws IOException;

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }
}
