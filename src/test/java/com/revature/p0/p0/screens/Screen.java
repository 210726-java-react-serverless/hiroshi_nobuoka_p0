package com.revature.p0.p0.screens;

import com.revature.p0.p0.respositries.Repository;

public abstract class Screen {

    protected String name;
    protected String route;

    public Screen(String name, String route, Repository repo){
        this.name = name;
        this.route= route;
    }

    public abstract void render();

}
