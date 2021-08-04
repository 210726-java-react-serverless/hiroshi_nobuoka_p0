package com.revature.p0.p0.screens;

public class WelcomeScreen extends Screen {
    protected String name;
    protected String route;

    public WelcomeScreen(){
        super("Welcome Screen", "/welcome");
    }

    public void render(){
        String menu = "Welcome to the Course Management Application!\n"+
                "\nPlease select from the following:\n"+
                "1) Login\n" +
                "2) Register\n"+
                "3) Exit\n"+
                ">";
        System.out.print(menu);

        
    }
}
