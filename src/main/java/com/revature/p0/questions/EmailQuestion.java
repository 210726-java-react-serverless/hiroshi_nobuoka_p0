package com.revature.p0.questions;

public class EmailQuestion extends Question{
    protected String question;

    public EmailQuestion(String string){
        super(string);
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty())
            return false;
        if(answer.indexOf("@") == -1)
            return false;
        if(!answer.endsWith(".com"))
            return false;
        return true;
    }
}
