package com.revature.p0.p0.questions;

public class EduQuestion extends Question {
    public String question;
    public String answer;

    public EduQuestion(){
        super("Are you a student or a faculty member? Enter 'student' or 'faculty' below:\n>");
    }
    public boolean validAnswer(String answer){
        if(answer.trim().toLowerCase().equals("student")) {
            this.answer = "student";
            return true;
        }
        if(answer.trim().toLowerCase().equals("faculty")) {
            this.answer = "faculty";
            return true;
        }
        System.out.println("Please enter a valid entry.\n");
        return false;
    }
}
