package com.revature.p0.questions;

public class Question{
    protected String question;

    public Question(String question){
        this.question= question;
    }
    boolean validAnswer(String answer){
        if(answer.trim().equals(""))
            return false;
        return true;
    };
}
//TODO create question classes that extend Question and provide their own validation logic.
//TODO create a question-generator class, and use in RegisterScreen.