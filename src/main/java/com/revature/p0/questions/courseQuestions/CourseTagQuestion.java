package com.revature.p0.questions.courseQuestions;

import com.revature.p0.questions.Question;
import com.revature.p0.services.CourseService;

public class CourseTagQuestion extends Question {
    private static final String type = "coursetag";
    private String question = "Enter the tag for the course. \nShould be a 7 character tag with a three capital letter prefix and a four digit course number (e.g. CIS3005): ";
    private CourseService service;

    public CourseTagQuestion(CourseService service){
        this.service = service;
        System.out.println(question);
    }

    @Override
    public boolean validAnswer(String answer) {
        if(!answer.matches("[A-Z]{3}[0-9]{4}")) {
            System.out.println("Tag must start with three capital letters and end with four numbers. Try again: ");
            return false;
        }
        if(!service.courseTagAvailable(answer)){
            System.out.println("Tag already being used. Please enter a different one:");
            return false;
        }
        return true;
    }

    public boolean validAnswer(String answer, String delete){
        if(service.courseTagAvailable(answer)){
            System.out.println("Course tag does not exist. Please try again: ");
            return false;
        } return true;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
