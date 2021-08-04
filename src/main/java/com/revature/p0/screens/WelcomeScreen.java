package com.revature.p0.screens;

import com.revature.p0.respositries.Repository;
import com.revature.p0.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

public class WelcomeScreen extends Screen{
    private BufferedReader reader;
    protected ScreenRouter screenRouter;

    public WelcomeScreen(ScreenRouter screenRouter, BufferedReader reader){
        super("Welcome Screen", "/welcome");
        this.screenRouter = screenRouter;
        this.reader = reader;
    }


    public void render() throws IOException {
        String menu = "Welcome to the Course Management Application!\n"+
                "\nPlease select from the following:\n"+
                "1) Login\n" +
                "2) Register\n"+
                "3) Exit\n"+
                ">";
        System.out.print(menu);
        String response = reader.readLine();
        if(response.equals("1"))
            screenRouter.changeCurrent("/login");
        if(response.equals("2"))
            screenRouter.changeCurrent("/register");
        if(response.equals("3"))
            System.out.println("exit");//TODO make this work
            

        
    }
}
