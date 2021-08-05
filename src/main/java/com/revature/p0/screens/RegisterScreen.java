package com.revature.p0.screens;

import com.revature.p0.POJO.Faculty;
import com.revature.p0.POJO.Student;
import com.revature.p0.questions.*;
import com.revature.p0.respositries.Repository;
import com.revature.p0.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

public class RegisterScreen extends Screen {
    private BufferedReader reader;
    private Repository repo;
    private Student student;
    private Faculty faculty;
    String[] infoArray = new String[6];


    public RegisterScreen(BufferedReader reader, Repository repo){
        super("Register Screen", "/register");
        this.reader = reader;
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

        if(repo.name.equals("student")) {
            student = new Student(infoArray);
            ScreenRouter.navigate("/login", student);
        }
        if(repo.name.equals("faculty")){
            faculty = new Faculty(infoArray);
            ScreenRouter.navigate("/login", faculty);
        }
    }
}
