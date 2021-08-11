package com.revature.p0.util;


import com.revature.p0.documents.Course;
import com.revature.p0.repositories.CourseRepository;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.screens.*;

import com.revature.p0.services.CourseService;
import com.revature.p0.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {
    static final Logger logger = LogManager.getLogger(AppState.class);
    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        UserSession session = new UserSession();
        UserRepository repo = new UserRepository(session);
        CourseRepository courseRepo = new CourseRepository(session);
        CourseService courseService = new CourseService(courseRepo, session);
        UserService service = new UserService(repo, session);
        UserService serviceLink = new UserService(repo, session, courseService);


        router.addScreen(new WelcomeScreen(reader, router));
        router.addScreen(new LoginScreen(reader, router, service, session));
        router.addScreen(new RegisterScreen(reader,router,service, session));
        router.addScreen(new StudentDashboard(reader,router, courseService, session));
        router.addScreen(new FacultyDashboard(reader, router));
        router.addScreen(new UpdateScreen(reader,router, serviceLink, session));
        router.addScreen(new FacultyRegistrationScreen(reader,router,courseService,session));
        router.addScreen(new CourseCatalogScreen(reader,router,courseService,session));
    }

    public void startup() {
                 
        router.navigate("/welcome");

        while (appRunning) {
            try {
                router.getCurrentScreen().render();
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("Something went wrong.");
            }
        }
    }
}
