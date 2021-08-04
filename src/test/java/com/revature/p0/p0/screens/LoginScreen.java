package com.revature.p0.p0.screens;

import com.revature.p0.p0.respositries.Repository;

public class LoginScreen extends Screen {
    protected String name;
    protected String route;

    public LoginScreen(Repository repo){
        super("Login Screen", "/login", repo);
    }

    public void render(){
        System.out.println("Are you a student or faculty member? Type either 'student' or 'faculty' below: \n>");
        String edu = reader.readLine();
        System.out.println("Enter your username: ");
        String userName = reader.readLine();
        System.out.println("Enter your password: ");
        String password = reader.readLine();
        if(edu.trim().toLowerCase().equals("student"))
            if(repo.readStudentLogin())//make readLogin an interface that's implemented by studentLoginRead and facultyLoginRead
                router.navigate("/catalog");
            else{
                System.out.println("Invalid credentials entered.\n");
                router.navigate("/welcome");
            }
        if(edu.trim().toLowerCase().equals("faculty"))
            if(repo.readFacultyLogin())
                router.navigate("/catalog");
            else {
                System.out.println("Invalid credentials entered.\n");
                router.navigate("/welcome");
            }
        else {
            System.out.println("Neither 'student' nor 'faculty' entered. Login unsuccessful.");
            router.navigate("/welcome");
        }
    }
}
}
