package com.revature.p0.questions;

public abstract class Question{
    private String question;
    private String type;

    public abstract boolean validAnswer(String answer);

}

