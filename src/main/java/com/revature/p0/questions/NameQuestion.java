package com.revature.p0.questions;

public class NameQuestion extends Question{
    protected String question;

    public NameQuestion(String string){
        super(string);
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty())
            return false;
        if(!answer.matches("[a-z]"))
            return false;
        return true;
    }
}
