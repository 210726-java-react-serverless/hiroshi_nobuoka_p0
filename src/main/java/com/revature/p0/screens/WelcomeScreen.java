package com.revature.p0.screens;

import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.BufferedReader;



public class WelcomeScreen extends Screen{
    static final Logger logger = LogManager.getLogger(WelcomeScreen.class);
    private BufferedReader reader;
    protected ScreenRouter screenRouter;

    public WelcomeScreen(BufferedReader reader, ScreenRouter screenRouter){
        super("Welcome Screen", "/welcome", reader, screenRouter);
        this.reader = reader;
        this.screenRouter = screenRouter;
        }

    public void render() throws Exception {
        String menu = "Welcome to the Hiroshi's Console Application!\n"+
                "\nPlease select from the following:\n"+
                "1) Login\n" +
                "2) Register\n"+
                "3) Exit\n";
        System.out.print(menu);

        NavigateScreenQuestion askUserInput = new NavigateScreenQuestion(3);
        String userInput = reader.readLine();
        while(!askUserInput.validAnswer(userInput)){
            userInput = reader.readLine();
        }
        switch(userInput){
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

