package com.revature.p0.questions;

import com.revature.p0.documents.AppUser;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EduQuestion extends Question{
    private String type;
    private String question = "Are you a student or a faculty member? Enter 'student' or 'faculty' below:\n>";
    private UserSession session;

    public EduQuestion(UserSession session){this.session = session;}

    public boolean validAnswer(String answer){
        if(answer.trim().toUpperCase().equals(AppUser.Edu.STUDENT.toString())) {
            session.setEducation(AppUser.Edu.STUDENT);
            return true;
        }
        if(answer.trim().toUpperCase().equals(AppUser.Edu.FACULTY.toString())) {
            session.setEducation(AppUser.Edu.STUDENT);
            return true;
        }
        System.out.println("Please enter either 'student' or 'faculty'.\n");
        return false;
    }


    public void getQuestion() {System.out.println(question);}

    public void setQuestion(String question) {
        this.question = question;
    }

}
