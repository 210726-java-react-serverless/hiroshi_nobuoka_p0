package com.revature.p0.questions.userQuestions;

import com.revature.p0.questions.Question;
import sun.security.util.Password;

public class PasswordQuestion extends Question {
    private static final String type = "password";
    private String question ="Enter your password: ";

    public PasswordQuestion(){
        System.out.println(question);
    }

    @Override
    public boolean validAnswer(String answer){
        if(answer.trim().equals("")||answer.isEmpty()) {
            System.out.println("Cannot be empty.");
            return false;
        }
        //set min length
        if(answer.length()<6) {
            System.out.println("Your password is too short.");
            return false;
        }
        //set special char requirement
        if(!answer.matches(".*[!\"#$%&\'()*+,./:;<=>?@^_|~].*")) {
            System.out.println("Special character required.");
            return false;
        }
        return true;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
