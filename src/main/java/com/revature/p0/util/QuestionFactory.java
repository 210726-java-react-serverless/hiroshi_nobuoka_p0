package com.revature.p0.util;

import com.revature.p0.questions.*;
import com.revature.p0.services.UserService;


public class QuestionFactory {
    private UserService service;

    /*
        Didn't include the Education question because of the UserSession dependency
        injection. Didn't include NavigateScreen question because of its dynamic
        numberOfOptions field.
     */
    public QuestionFactory(UserService service){
        this.service = service;
    }

    public Question getQuestion(String type){
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
}
