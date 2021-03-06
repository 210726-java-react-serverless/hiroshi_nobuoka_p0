package com.revature.p0.questions.userQuestions;

import com.revature.p0.questions.Question;

public class NameQuestion extends Question {
    private static final String type = "name";
    private String question;

    public NameQuestion(String question){
        this.question = question;
        System.out.println(question);
    }

    @Override
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()){
            System.out.println("Cannot be empty.");
            return false;
        }
        if(!answer.matches("[a-z]+")) {
            System.out.println("Only names with English letters are considered valid. Please try again.");
            return false;
        }
        return true;
    }

}
