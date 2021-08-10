package com.revature.p0.screens;

import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.util.ScreenRouter;


import java.io.BufferedReader;

public class StudentDashboard extends Screen{
    private ScreenRouter router;
    private BufferedReader reader;

    public StudentDashboard(BufferedReader reader, ScreenRouter router) {
        super("Student Dashboard", "/sdash", reader, router);
        this.router = router;
        this.reader = reader;
    }

    @Override
    public void render() throws Exception {
        System.out.println("You are on the Student Dashboard.\n");
        String menu = "1)Register for a course\n"+
                "2)Print schedule\n"+
                "3)Update personal info\n"+
                "4)Back\n";
        System.out.println(menu);

        NavigateScreenQuestion prompt = new NavigateScreenQuestion(4);
        String userInput = reader.readLine();
        while(!prompt.validAnswer(userInput))
            userInput = reader.readLine();

        switch(userInput){
            case "1":
                router.navigate("/sreg");
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
