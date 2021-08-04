package com.revature.p0.p0.questions;

public class NameQuestion extends Question {
    public String question;

    public NameQuestion(String string){
        super(string);
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()){
            System.out.println("Cannot be empty.\n");
            return false;
        }
        if(!answer.matches("[a-z]")) {
            System.out.println("Only names with English letters are considered valid. Please try again.\n");
            return false;
        }
        return true;
    }
}
