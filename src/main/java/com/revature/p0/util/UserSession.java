package com.revature.p0.util;

import com.revature.p0.documents.AppUser;

public class UserSession {

    private AppUser currentUser;
    private AppUser.EDU education;

    public void setEducation(AppUser.EDU education){this.education = education;}


    public AppUser.EDU getEducation() {return education;}

    public AppUser getCurrentUser(){return currentUser;}

    public void setCurrentUser(AppUser currentUser) {this.currentUser = currentUser;}

    public boolean isActive(){return currentUser != null;}

    public void closeSession(){setCurrentUser(null);}


}
