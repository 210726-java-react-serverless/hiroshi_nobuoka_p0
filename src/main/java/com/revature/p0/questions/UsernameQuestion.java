package com.revature.p0.questions;

import com.revature.p0.services.UserService;

public class UsernameQuestion extends Question{
    private static final String type = "username";
    private String question ="Enter your username: ";
    private UserService service;
    public UsernameQuestion(UserService service){
        this.service = service;
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()) {
            //TODO include Log for answer
            System.out.println("Cannot be empty.\n");
            return false;
        }
        if(!service.usernameAvailable(answer)) {
            System.out.println("Username not available.\n");
            return false;
        }
        return true;
    }

    public void getQuestion() {System.out.println(question);}

    public void setQuestion(String question) {
        this.question = question;
    }
}
