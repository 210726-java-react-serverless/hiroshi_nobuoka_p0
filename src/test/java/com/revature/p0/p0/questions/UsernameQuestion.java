package com.revature.p0.p0.questions;

import com.revature.p0.p0.respositries.Repository;

public class UsernameQuestion extends Question {
    public String question;
    public Repository repo;


    public UsernameQuestion(String question) {super(question);}

    public boolean validAnswer(String answer){

        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.\n");
            return false;
        }
        //log here
        if(!repo.search()) {
            System.out.println("Username is already taken.\n");
            return false;
        }
        return true;
    }
}
