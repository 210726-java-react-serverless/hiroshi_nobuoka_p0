package com.revature.p0.questions.courseQuestions;

import com.revature.p0.questions.Question;
import com.revature.p0.services.CourseService;
import com.revature.p0.services.UserService;

public class CourseNameQuestion extends Question {
    private static final String type = "coursename";
    private String question = "Enter the full name of the course: ";
    private CourseService service;

    public CourseNameQuestion(CourseService service){
        this.service = service;
        System.out.println(question);
    }

    @Override
    public boolean validAnswer(String answer) {
        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.");
            return false;
        }
        if(!answer.matches("[a-z]+")) {
            System.out.println("Only names with English letters are considered valid. Please try again.");
            return false;
        }
        return true;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public CourseService getService() {
        return service;
    }

    public void setService(CourseService service) {
        this.service = service;
    }
}
