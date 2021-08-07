package com.revature.p0.questions;

public abstract class Question{
    public String question;


    public Question(String question){
        this.question= question;
    }
    public abstract boolean validAnswer(String answer);

    public String getQuestion() {
        return question;
    }


}

