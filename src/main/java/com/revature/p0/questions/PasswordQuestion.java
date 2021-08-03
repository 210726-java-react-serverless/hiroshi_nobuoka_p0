package com.revature.p0.questions;

public class PasswordQuestion extends Question{
    protected String question;

    public PasswordQuestion(){
        super("Create a password. Your password must be at least 6 characters in length and include at least one special character.");
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
