package com.revature.p0.util;

import com.revature.p0.exceptions.InvalidArgumentException;
import com.revature.p0.questions.*;
import com.revature.p0.services.UserService;


public class QuestionFactory {
    private UserService service;

    //didn't include education question because it would require a userSession dependency injection. EduQuestion will
    //be instantiated separately.
    public QuestionFactory(UserService service){
        this.service = service;
    }

    public Question getQuestion(String type){
        if(type.equals("email"))
            return new EmailQuestion(service);
        if(type.equals("firstname")){
            NameQuestion firstname = new NameQuestion();
            firstname.setQuestion("Enter your first name: ");
            return firstname;
        }
        if(type.equals("lastname")){
            NameQuestion lastname = new NameQuestion();
            lastname.setQuestion("Enter your lastname name: ");
            return lastname;
        }
        if(type.equals("password"))
            return new PasswordQuestion();
        if(type.equals("username"))
            return new UsernameQuestion(service);
        else {
            //TODO log here
            throw new InvalidArgumentException("Cannot find question of " + type + " type");
        }
    }
}
