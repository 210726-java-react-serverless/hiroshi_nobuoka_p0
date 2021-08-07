package com.revature.p0.questions;

public abstract class Question{
    private String question;
    private String name;

    public Question(String name, String question){
        this.name = name;
        this.question= question;
    }
    public abstract boolean validAnswer(String answer);

    public String getQuestion() {
        return question;
    }


}
//TODO create question classes that extend Question and provide their own validation logic.
//TODO create a question-generator class, and use in RegisterScreen.