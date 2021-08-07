package com.revature.p0.services;

import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.respositries.UserRepository;
import com.revature.p0.util.UserSession;

public class UserService {
    private final UserRepository repo;
    private UserSession session;

    public UserService(UserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

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
