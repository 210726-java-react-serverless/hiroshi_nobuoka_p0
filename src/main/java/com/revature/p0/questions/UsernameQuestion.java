package com.revature.p0.questions;

import com.revature.p0.respositries.Repository;
import com.revature.p0.respositries.StudentRepository;

public class UsernameQuestion extends Question{
    private String question;
    private Repository repo;


    public UsernameQuestion(String question, Repository repo) {
        super(question);
        if(repo.name.equals("student"))
            this.repo = (StudentRepository) repo;
        if(repo.name.equals("faculty"))
            this.repo = (FacultyRepository) repo;
    }

    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.\n");
            return false;
        }
        //log here
        if(!repo.exists(answer)) {
            System.out.println("Username is already taken.\n");
            return false;
        }
        return true;
    }
}
