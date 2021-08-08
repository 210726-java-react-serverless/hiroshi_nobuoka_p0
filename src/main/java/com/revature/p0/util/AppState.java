package com.revature.p0.util;

import com.revature.p0.questions.EduQuestion;
import com.revature.p0.questions.Question;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.screens.WelcomeScreen;
import com.revature.p0.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {
    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        UserSession userSession = new UserSession();
        UserRepository userRepo = new UserRepository(userSession);
        UserService userService = new UserService(userRepo, userSession);

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
