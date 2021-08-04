package com.revature.p0.p0.util;

import com.revature.p0.p0.questions.EduQuestion;
import com.revature.p0.p0.questions.Question;
import com.revature.p0.p0.screens.LoginScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ScreenRouter router = new ScreenRouter();

    router.addScreen(new LoginScreen());
    public void startup(){
        System.out.println("Welcome to Project Zero. Do you want to login or register?\n");
        String choice = reader.readLine();

        Question eduQ = new EduQuestion();
        String edu = reader.readLine();
        while(eduQ.validAnswer())
            edu = reader.readLine();

        
        if(choice.equals("login"))
            router.addScreen(new LoginScreen());
        else if(choice.equals("register"))
            router.addScreen()
    }
}
