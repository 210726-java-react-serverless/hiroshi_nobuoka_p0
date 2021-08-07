package com.revature.p0.util;

import com.revature.p0.documents.AppUser;

public class UserSession {

    private AppUser currentUser;
    private AppUser.Edu education;

    public AppUser.Edu getEducation() {return education;}

    public AppUser getCurrentUser(){return currentUser;}

    public void setEducation(AppUser.Edu education){this.education = education;}

    public void setCurrentUser(AppUser currentUser) {this.currentUser = currentUser;}

    public boolean isActive(){return currentUser != null;}

    public void closeSession(){setCurrentUser(null);}


}
