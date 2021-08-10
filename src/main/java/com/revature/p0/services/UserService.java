package com.revature.p0.services;

import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.InstantiateUserException;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepository repo;
    private UserSession session;

    public UserService(UserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

    public AppUser createAppUser(String[] answerArray) throws ArrayIndexOutOfBoundsException{
        if(session.getEducation() == AppUser.EDU.STUDENT) {
            AppUser student = new AppUser(AppUser.EDU.STUDENT, answerArray[0], answerArray[1], answerArray[2], answerArray[3], answerArray[4]);
            session.setCurrentUser(student);
            return student;
        } else if(session.getEducation() == AppUser.EDU.FACULTY) {
            AppUser faculty = new AppUser(AppUser.EDU.FACULTY, answerArray[0], answerArray[1], answerArray[2], answerArray[3], answerArray[4]);
            session.setCurrentUser(faculty);
            return faculty;
        } else {
            logger.debug("getEducation() isn't returning student or faculty");
            return null;
        }
    }

    //calls the appropriate repo method, depending on the newOrUpdate argument
    public void register(AppUser user, String newOrUpdate) {
        if (newOrUpdate.equals("new"))
            repo.save(user);
        else if(newOrUpdate.equals("update"))
            repo.update(user);
        else
            throw new IllegalArgumentException("Argument for register() method must be 'new' or 'update'");
    }

    public boolean usernameAvailable(String username){
        if (repo.findUserByUsername(username) == null)
            return true;
        return false;
    }

    public boolean emailAvailable(String email){
        if(repo.findUserByEmail(email) == null)
            return true;
        return false;
    }

    public AppUser login(String username, String password){
        AppUser authUser = repo.findUserByCredentials(username, password);
        if(authUser != null)
            session.setCurrentUser(authUser);
        return authUser;
    }

    

}
