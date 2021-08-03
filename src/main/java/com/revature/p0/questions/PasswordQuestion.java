package com.revature.p0.questions;

public class PasswordQuestion extends Question{
    protected String question;

    public PasswordQuestion(String string){
        super(string);
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty())
            return false;
        //set min length
        if(answer.length()<6)
            return false;
        //set special char requirement
        if(!answer.matches(".*[!\"#$%&\'()*+,./:;<=>?@^_|~].*"))
            return false;
        return true;
    }
}
