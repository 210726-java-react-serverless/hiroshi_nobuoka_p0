package com.revature.p0.questions;

public class PasswordQuestion extends Question{
    public String question;

    public PasswordQuestion(String question){
        super(question);
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.\n");
            return false;
        }
        //set min length
        if(answer.length()<6) {
            System.out.println("Your password is too short.\n");
            return false;
        }
        //set special char requirement
        if(!answer.matches(".*[!\"#$%&\'()*+,./:;<=>?@^_|~].*")) {
            System.out.println("Special character required.\n");
            return false;
        }
        return true;
    }
}
