package com.revature.p0.util;

import com.revature.p0.questions.*;
import com.revature.p0.questions.userQuestions.EmailQuestion;
import com.revature.p0.questions.userQuestions.NameQuestion;
import com.revature.p0.questions.userQuestions.PasswordQuestion;
import com.revature.p0.questions.userQuestions.UsernameQuestion;
import com.revature.p0.services.UserService;


public class QuestionFactory {
    private static final QuestionFactory questionFactory = new QuestionFactory();
    /*
        Didn't include the Education question because of the UserSession dependency
        injection. Didn't include NavigateScreen question because of its dynamic
        numberOfOptions field.
     */
    private QuestionFactory(){}

    public Question getQuestion(String type, UserService service){
        if(type.equals("email"))
            return new EmailQuestion(service);
        if(type.equals("firstname")){
            NameQuestion firstname = new NameQuestion("Enter your first name: ");
            return firstname;
        }
        if(type.equals("lastname")){
            NameQuestion lastname = new NameQuestion("Enter your last name: ");
            return lastname;
        }
        if(type.equals("password"))
            return new PasswordQuestion();
        if(type.equals("username"))
            return new UsernameQuestion(service);
        else {
            //TODO log here
            throw new IllegalArgumentException("Cannot find question of " + type + " type");
        }
    }

    public static QuestionFactory getInstance(){ return questionFactory; }
}
