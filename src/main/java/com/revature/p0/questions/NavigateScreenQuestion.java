package com.revature.p0.questions;

public class NavigateScreenQuestion extends Question{
    private static final String type = "navigate";
    private String question = "Please enter an appropriate value: ";
    private int numberOfOptions;

    public NavigateScreenQuestion(int numberOfOptions){
        this.numberOfOptions = numberOfOptions;
        System.out.println(question);
    }
    @Override
    public boolean validAnswer(String answer) {
        if(!answer.matches("[1-"+numberOfOptions+"]")){
            System.out.println("Please enter a valid number.");
            return false;
        }
        return true;
    }

    public int getNumberOfOptions() {return numberOfOptions;}
}
