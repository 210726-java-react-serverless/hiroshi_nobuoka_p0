package com.revature.p0.screens;

import com.revature.p0.questions.EduQuestion;
import com.revature.p0.questions.EmailQuestion;
import com.revature.p0.questions.NameQuestion;
import com.revature.p0.questions.Question;

public class RegisterScreen extends Screen {
    protected String name;
    protected String route;

    public RegisterScreen(){
        super("Register Screen", "/register");
    }

    public void render(){
        Question[] qArray = {
                new EduQuestion(),
                new NameQuestion("What is your first name?"),
                new NameQuestion("What is your last name?"),
                new EmailQuestion(),

        }
        //Probably won't use below code, but keeping just in case.
       /* String[] regInfoArray = new String[6];
        String[] questionArray = {"Are you a student or a faculty member? Enter 'student' or 'faculty' below: \n>",
                "Enter your first name: ",
                "Enter your last name: ",
                "Enter your email address: ",
                "Create a username: ",
                "Enter your password: "};
        for(String question: questionArray)
            while(true)
        while(true) {
            System.out.println("Are you a student or a faculty member? Enter 'student' or 'faculty' below: \n>");
            edu = reader.readLine();
            if (edu.trim().toLowerCase().equals("student") || edu.trim().toLowerCase().equals("faculty"))
                break;
        }
        System.out.println("Enter your first name: ");
        String firstName = reader.readLine();
        System.out.println("Enter your last name: ");
        String lastName = reader.readLine();
        System.out.println("Enter your email address: ");
        String email = reader.readLine();
        System.out.println("Create a username: ");

            String username = reader.readLine();
        System.out.println("Enter your password: ");
        String password = reader.readLine();


        if(edu.trim().toLowerCase().equals("student"))
            pojoMaker.createStudent(firstName, lastName, email, username, password);
    */
    }
}
