package com.revature.p0.screens;

import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;

import java.io.BufferedReader;

public class LoginScreen extends Screen {
    private final UserService service;
    private BufferedReader reader;
    private ScreenRouter router;
    private final QuestionFactory qfactory;

    public LoginScreen(BufferedReader reader, ScreenRouter router, UserService service) {
        super("LoginScreen", "/login", reader, router);
        this.service = service;
        qfactory = new QuestionFactory(service);
    }

    @Override
    public void render() throws Exception {

        String menu = "\nUser Login\n" +
                "1) Login\n" +
                "2) Go Back\n" +
                ">";
        System.out.println(menu);
        NavigateScreenQuestion askUserInput = new NavigateScreenQuestion(2);
        String userInput = reader.readLine();
        while(!askUserInput.validAnswer(userInput)){
            userInput = reader.readLine();
        }
        switch(userInput){
            case "1":
                break;
            case "2":
                router.previousScreen();
                return;
        }

        qfactory.getQuestion("username");
        String username = reader.readLine();
        qfactory.getQuestion("password");
        String password = reader.readLine();
        if(service.login(username, password)) {
            System.out.println("Login successful!\n");
            router.navigate("/welcome");
        } else {
            System.out.println("Login attempt failed. Please try again.\n");
            router.navigate("/login");
        }
    }
}
