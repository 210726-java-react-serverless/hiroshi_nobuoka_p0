package com.revature.p0.screens;

import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.util.ScreenRouter;

import java.io.BufferedReader;

public class FacultyDashboard extends Screen{
    private ScreenRouter router;
    private BufferedReader reader;

    public FacultyDashboard(BufferedReader reader, ScreenRouter router) {
        super("Faculty Dashboard", "/fdash", reader, router);
        this.router = router;
        this.reader = reader;
    }

    @Override
    public void render() throws Exception {
        System.out.println("You are on the Faculty Dashboard.\n");
        String menu = "1)Create/delete a course\n"+
                "2)Update personal info\n"+
                "3)Back\n";
        System.out.println(menu);

        NavigateScreenQuestion prompt = new NavigateScreenQuestion(3);
        String userInput = reader.readLine();
        while(!prompt.validAnswer(userInput))
            userInput = reader.readLine();

        switch(userInput){
            case "1":
                router.navigate("/freg");
                break;
            case "2":
                router.navigate("/update");
                break;
            case "3":
                router.previousScreen();
        }
    }
}
