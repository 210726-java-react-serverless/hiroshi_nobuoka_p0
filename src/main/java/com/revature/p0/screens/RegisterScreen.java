package com.revature.p0.screens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.*;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;


import java.io.BufferedReader;

public class RegisterScreen extends Screen {
    static final Logger logger = LoggerFactory.getLogger(RegisterScreen.class);
    private BufferedReader reader;
    private final UserService service;
    private UserSession session;


    public RegisterScreen(BufferedReader reader, ScreenRouter router, UserService service, UserSession session){
        super("Register Screen", "/register",reader,router);
        this.service = service;
        this.session = session;
        this.reader = reader;
    }

    public void render() throws Exception {
        //logger.info("Starting method execution: " + methodSignature.getD)
        String menu = "You're on the register screen. \n"
                + "\n 1)Register \t 2)Return";

        System.out.println(menu);
        //factory doesn't include NavigateScreen question b/c of its dynamic argument.
        Question prompt = new NavigateScreenQuestion(2);
        String userInput = reader.readLine();
        while (!prompt.validAnswer(userInput)) {
            userInput = reader.readLine();
        }

        switch(userInput){
            case "1":
                break;
            case "2":
                router.previousScreen();
                return;
        }

        EduQuestion eduQuestion = new EduQuestion(session);
        String edu = reader.readLine();
        while(!eduQuestion.validAnswer(edu)){
            edu = reader.readLine();
        }

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

    }

}