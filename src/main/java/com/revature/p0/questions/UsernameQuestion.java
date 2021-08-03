package com.revature.p0.questions;

public class UsernameQuestion extends Question{
    protected String question;
    protected Repository repo;

    //Depending on eduquestion.value, repo can be set to a StudentRepo or FacultyRepo in RegisterScreen
    public UsernameQuestion(String string, Repository repo){
        super("Create a username.");
        this.repo = repo;
    }
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.\n");
            return false;
        }
        //CRUDRepo interface will have a searchUsername() method
        if(!repo.searchUsername()) {
            System.out.println("Username is already taken.\n");
            return false;
        }
        return true;
    }
}
