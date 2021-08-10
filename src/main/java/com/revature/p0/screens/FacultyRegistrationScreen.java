package com.revature.p0.screens;

import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.questions.Question;
import com.revature.p0.services.UserService;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class FacultyRegistrationScreen extends Screen{
    static final Logger logger = LogManager.getLogger(RegisterScreen.class);
    private final UserService service;
    private UserSession session;

    public FacultyRegistrationScreen(BufferedReader reader, ScreenRouter router, UserService service, UserSession session){
        super("Faculty Registration Screen", "/freg",reader,router);
        this.service = service;
        this.session = session;
    }

    @Override
    public void render() throws Exception {
        String courseList = "List courses here\n";
        String menu = "You're on the course management screen.\n" +
                "1)Create a course\n" +
                "2)Remove a course\n" +
                "3)Back\n";

        System.out.println(courseList);
        System.out.println(menu);

        Question prompt = new NavigateScreenQuestion(3);
        String userInput = reader.readLine();
        while (!prompt.validAnswer(userInput)) {
            userInput = reader.readLine();
        }

        switch (userInput) {
            case "1":
                break;
            case "2":
                break;
            case "3:":
                router.previousScreen();
        }
    }
}
