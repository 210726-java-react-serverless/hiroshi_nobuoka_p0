package com.revature.p0.screens;

import com.revature.p0.questions.Question;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;

import java.io.BufferedReader;

public class StudentDashboard extends Screen{
    private UserService service;
    private ScreenRouter router;
    private UserSession session;
    private BufferedReader reader;
    QuestionFactory qFactory = QuestionFactory.getInstance();


    public StudentDashboard(BufferedReader reader, ScreenRouter router, UserSession session, UserService service) {
        super("Student Dashboard", "/sdash", reader, router);
        this.service = service;
        this.router = router;
        this.session = session;
        this.reader = reader;
    }

    @Override
    public void render() throws Exception {
        String menu = "1)Register for classes\n"+
                "2)Print schedule\n"+
                "3)Update personal info\n"+
                "4)Back";
        System.out.println(menu);

        Question userInputQuestion = qFactory.getQuestion("navigate",service);
        String userInput = reader.readLine();
        while(!userInputQuestion.validAnswer(userInput))
            userInput = reader.readLine();

        switch(userInput){
            case "1":
                router.navigate("/class");
                break;
            case "2":
                //TODO add schedule implementation
                break;
            case "3":
                router.navigate("/update");
                break;
            case "4":
                router.previousScreen();
        }
    }
}
