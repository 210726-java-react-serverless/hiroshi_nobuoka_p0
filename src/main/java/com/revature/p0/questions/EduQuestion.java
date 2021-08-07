package com.revature.p0.questions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EduQuestion extends Question{
    public static final Logger logger = LogManager.getLogger(EduQuestion.class);

    private final String type = "education";
    private String question = "Are you a student or a faculty member? Enter 'student' or 'faculty' below:\n>";
    private String answer;


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

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
