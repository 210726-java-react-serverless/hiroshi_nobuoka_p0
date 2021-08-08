package com.revature.p0.util;


import com.revature.p0.repositories.UserRepository;
import com.revature.p0.screens.*;

import com.revature.p0.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {
    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        UserSession session = new UserSession();
        UserRepository repo = new UserRepository(session);
        UserService service = new UserService(repo, session);

        router.addScreen(new WelcomeScreen(reader, router));
        router.addScreen(new LoginScreen(reader, router, service));
        router.addScreen(new RegisterScreen(reader,router,service));
    }

    public void startup() {
                 
        router.navigate("/welcome");

        while (appRunning) {
            try {
                router.getCurrentScreen().render();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
