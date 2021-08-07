package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.*;
import com.revature.p0.services.UserService;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;

import java.io.BufferedReader;
import java.io.IOException;


public class RegisterScreen extends Screen {
    private BufferedReader reader;
    private UserService service;
    private AppUser user;
    private UserSession session;
    String[] infoArray = new String[5];


    public RegisterScreen(BufferedReader reader, UserService service, UserSession session){
        super("Register Screen", "/register");
        this.reader = reader;
        this.session = session;
    }

    public void render() throws IOException {

        Question[] qArray = {
                new NameQuestion("What is your first name?"),
                new NameQuestion("What is your last name?"),
                new EmailQuestion("What is your email address?"),
                new UsernameQuestion(),//TODO INCORPORATE REPO WITH QUESTION LOGIC
                new PasswordQuestion("What is your password?")
        };

        for(int i=0; i< qArray.length; i++){
            Question q = qArray[i];
            System.out.println(q.question);
            String answer = reader.readLine();
            while(!q.validAnswer(answer)) {
                answer = reader.readLine();
            }
            infoArray[i] = answer;

        }

        if(session.getEducation() == AppUser.Edu.STUDENT) {
            AppUser student = new AppUser(AppUser.Edu.STUDENT, infoArray[1], infoArray[2], infoArray[3], infoArray[4], infoArray[5]);
            session.setCurrentUser(student);
            ScreenRouter.navigate("/welcome");
        }
        if(session.getEducation() == AppUser.Edu.FACULTY)){
            AppUser faculty = new AppUser(AppUser.Edu.FACULTY, infoArray[1], infoArray[2], infoArray[3], infoArray[4], infoArray[5]);
            session.setCurrentUser(faculty);
            ScreenRouter.navigate("/welcome");
        }
    }
}
