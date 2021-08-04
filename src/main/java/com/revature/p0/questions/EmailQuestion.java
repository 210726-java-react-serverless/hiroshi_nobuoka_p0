package com.revature.p0.questions;

public class EmailQuestion extends Question{
    public String question;

    public EmailQuestion(){
        super("What is your email address?");
    }
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
        return true;
    }
}
