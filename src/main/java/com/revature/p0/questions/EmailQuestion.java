package com.revature.p0.questions;

import com.revature.p0.services.UserService;

public class EmailQuestion extends Question{
    private static final String type = "email";
    private String question = "Enter your email address: ";
    private UserService service;

    public EmailQuestion(UserService service){
        this.service = service;
        System.out.println(question);
    }

    @Override
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.\n");
            return false;
        }
        if(answer.indexOf("@") == -1) {
            System.out.println("Must enter a valid email address.\n");
            return false;
        }
        if(!answer.endsWith(".com")) {
            System.out.println("Must enter a valid email address.\n");
            return false;
        }
        if(!service.emailAvailable(answer)){
            System.out.println("Email already being used.");
            return false;
        }
        return true;
    }


    public void setQuestion(String question) {this.question = question;}
}
