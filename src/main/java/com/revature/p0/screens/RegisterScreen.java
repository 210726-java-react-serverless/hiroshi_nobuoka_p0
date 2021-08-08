package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.*;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;


import java.io.BufferedReader;

public class RegisterScreen extends Screen {
    private BufferedReader reader;
    private final UserService service;




    public RegisterScreen(BufferedReader reader, ScreenRouter router, UserService service){
        super("Register Screen", "/register",reader,router);
        this.service = service;

    }

    public void render() throws Exception {
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