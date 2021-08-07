package com.revature.p0.services;

import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.util.UserSession;

public class UserService {
    private final UserRepository repo;
    private UserSession session;

    public UserService(UserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }
    /*
        register() method only saves user to DB. This is because credential validation is handled by Questions classes.
        Since the validation for username and email are handled separately (i.e. in their respective Question's classes)
        preventing duplication is also handled separately by the usernameAvailable and emailAvailable methods.
    */
    public void register(AppUser newUser){
        repo.save(newUser);
    }



    public boolean usernameAvailable(String username){

        if(repo.findUserByUsername(username) != null)
            return true;
        else
            return false;
    }

    public boolean emailAvailable(String email){
        if(repo.findUserByEmail(email) != null)
            return true;
        else
            return false;
    }

    public void login(String username, String password){
        AppUser authUser = repo.findUserByCredentials(username, password);
        if(authUser == null)
            throw new AuthenticationException("Username and/or password not found");
        session.setCurrentUser(authUser);
    }

    

}
