package com.revature.p0.questions;

public class EduQuestion extends Question{
    protected String question;

    public EduQuestion(String string){
        super(string);
    }
    public boolean validAnswer(String answer){
        if(answer.trim().toLowerCase().equals("student")|answer.trim().toLowerCase().equals("faculty"))
            return true;
        return false;
    }
}
