package com.revature.p0.questions;

public class NameQuestion extends Question{
    private static final String type = "name";
    private String question;

    public NameQuestion(){
        System.out.println(question);
    }

    @Override
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


    public void setQuestion(String question) {
        this.question = question;
    }
}
