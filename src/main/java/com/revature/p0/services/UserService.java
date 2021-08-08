package com.revature.p0.services;

import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.exceptions.InvalidArgumentException;
import com.revature.p0.exceptions.InstantiateUserException;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.util.UserSession;

public class UserService {
    private final UserRepository repo;
    private UserSession session;

    public UserService(UserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

    public AppUser createAppUser(String[] answerArray) throws Exception{
        if(session.getEducation() == AppUser.Edu.STUDENT) {
            AppUser student = new AppUser(AppUser.Edu.STUDENT, answerArray[1], answerArray[2], answerArray[3], answerArray[4], answerArray[5]);
            session.setCurrentUser(student);
            return student;
        } else if(session.getEducation() == AppUser.Edu.FACULTY) {
            AppUser faculty = new AppUser(AppUser.Edu.FACULTY, answerArray[1], answerArray[2], answerArray[3], answerArray[4], answerArray[5]);
            session.setCurrentUser(faculty);
            return faculty;
        } else
            throw new InstantiateUserException();
    }

    //calls the appropriate repo method, depending on the newOrUpdate argument
    public void register(AppUser user, String newOrUpdate) {
        if (newOrUpdate.equals("new"))
            repo.save(user);
        else if(newOrUpdate.equals("update"))
            repo.update(user);
        else
            throw new InvalidArgumentException("Argument for register() method must be 'new' or 'update'");
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

    public boolean login(String username, String password){
        AppUser authUser = repo.findUserByCredentials(username, password);
        if(authUser == null)
            return false;
        session.setCurrentUser(authUser);
        return true;
    }

    

}
