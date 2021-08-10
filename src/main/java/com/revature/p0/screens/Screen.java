package com.revature.p0.screens;

import com.revature.p0.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Screen {

    protected String name;
    protected String route;
    protected BufferedReader reader;
    protected ScreenRouter router;

    public Screen(String name, String route, BufferedReader reader, ScreenRouter router) {
        this.name = name;
        this.route = route;
        this.reader = reader;
        this.router = router;
    }

    public abstract void render() throws Exception;

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }
}
