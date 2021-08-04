package com.revature.p0.util;

import com.revature.p0.questions.EduQuestion;
import com.revature.p0.questions.Question;
import com.revature.p0.respositries.Repository;
import com.revature.p0.respositries.StudentRepository;
import com.revature.p0.screens.LoginScreen;
import com.revature.p0.screens.WelcomeScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ScreenRouter screenRouter = new ScreenRouter();
    RepoRouter repoRouter = new RepoRouter();

    repoRouter.addRepos(


            )

    public void startup(){
        //Determine view so that router can add screens with the right repo
        EduQuestion question = new EduQuestion();
        String edu = reader.readLine();
        while(question.validAnswer(edu))
            edu = reader.readLine();

        if(edu.equals("student"))
            screenRouter.add(new WelcomeScreen(screenRouter, reader)
                    .add(new )
        else
            screenRouter.add(


            )

    }
}
