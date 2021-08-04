package com.revature.p0.screens;

import com.revature.p0.POJO.Student;
import com.revature.p0.questions.*;
import com.revature.p0.respositries.Repository;

import java.io.BufferedReader;
import java.io.IOException;

public class RegisterScreen extends Screen {
    protected BufferedReader reader;
    String[] infoArray = new String[6];

    public RegisterScreen(BufferedReader reader){
        super("Register Screen", "/register");
        this.reader = reader;
    }

    public void render() throws IOException {

        Question[] qArray = {
                new NameQuestion("What is your first name?"),
                new NameQuestion("What is your last name?"),
                new EmailQuestion("What is your email address?"),
                new UsernameQuestion(),
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

        Student student = new Student(infoArray);
    }
}
