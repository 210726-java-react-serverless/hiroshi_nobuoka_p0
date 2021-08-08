package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.*;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;

import java.io.BufferedReader;
import java.io.IOException;

public class RegisterScreen extends Screen {
    private BufferedReader reader;
    private UserService service;
    private AppUser user;
    private UserSession session;
    private ScreenRouter router;


    public RegisterScreen(BufferedReader reader, UserService service, ScreenRouter router, UserSession session){
        super("Register Screen", "/register");
        this.reader = reader;
        this.service = service;
        this.router = router;
        this.session = session;
    }

    public void render() throws Exception {
        String menu = "You're on the register screen. \n"
                + "\n 1)Register \t 2)Return";

        //factory doesn't include NavigateScreen question b/c of its dynamic argument.
        Question prompt = new NavigateScreenQuestion(2);
        String userInput = reader.readLine();
        while (!prompt.validAnswer(userInput)) {
            userInput = reader.readLine();
        }


        if (userInput == "1") {
            QuestionFactory qFactory = new QuestionFactory(service);
            String[] questionTypeArray = {"firstname", "lastname", "email", "username", "password"};
            String[] answerArray = new String[questionTypeArray.length];

            for (int i = 0; i < questionTypeArray.length; i++) {
                Question question = qFactory.getQuestion(questionTypeArray[i]);
                String answer = reader.readLine();
                while (!question.validAnswer(answer)) {
                    answer = reader.readLine();
                }
                answerArray[i] = answer;

                AppUser newUser = service.createAppUser(answerArray);
                service.register(newUser, "new");
                router.navigate("/welcome");
            }
        } else
            router.previousScreen();
    }

}