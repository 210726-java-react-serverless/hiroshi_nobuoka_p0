package com.revature.p0.questions;

import com.revature.p0.documents.AppUser;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EduQuestion extends Question{
    static final Logger logger = LogManager.getLogger(EduQuestion.class);
    private String question = "Are you a student or a faculty member? Enter 'student' or 'faculty' below: >";
    private UserSession session;

    public EduQuestion(UserSession session){
        this.session = session;
        System.out.println(question);
    }

    public boolean validAnswer(String answer){
        if(answer.trim().toLowerCase().equals("student")) {
            session.setEducation(AppUser.EDU.STUDENT);
            return true;
        }
        if(answer.trim().toLowerCase().equals("faculty")) {
            session.setEducation(AppUser.EDU.FACULTY);
            return true;
        } else
            System.out.println("Please enter either 'student' or 'faculty'.");
        return false;
    }

}
