package com.revature.p0.screens;

import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

public class WelcomeScreen extends Screen{
    private BufferedReader reader;
    protected ScreenRouter screenRouter;

    public WelcomeScreen(BufferedReader reader, ScreenRouter screenRouter){
        super("Welcome Screen", "/welcome", reader, screenRouter);
        }

    public void render() throws IOException {
        String menu = "Welcome to the Hiroshi's Console Application!\n"+
                "\nPlease select from the following:\n"+
                "1) Login\n" +
                "2) Register\n"+
                "3) Exit\n"+
                ">";
        System.out.print(menu);

        NavigateScreenQuestion askUserInput = new NavigateScreenQuestion(3);
        String answer = reader.readLine();
        while(askUserInput.validAnswer(answer)){
            answer = reader.readLine();
        }
        switch(answer){
            case "1":
                screenRouter.navigate("/login");
                break;
            case "2":
                screenRouter.navigate("/register");
                break;
            case "3":
                System.exit(0);
            }
        }
}

