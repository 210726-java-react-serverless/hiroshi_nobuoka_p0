package com.revature.p0.services;

import com.revature.p0.documents.AppUser;
import com.revature.p0.documents.Course;
import com.revature.p0.exceptions.InstantiateUserException;
import com.revature.p0.repositories.CourseRepository;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService {
    static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepository repo;
    private UserSession session;
    private CourseService courseService;

    public UserService(UserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

    public UserService(UserRepository repo, UserSession session, CourseService courseService) {
        this.repo = repo;
        this.session = session;
        this.courseService = courseService;
    }

    public AppUser createAppUser(String[] answerArray) throws ArrayIndexOutOfBoundsException {
        if (session.getEducation() == AppUser.EDU.STUDENT) {
            AppUser student = new AppUser(AppUser.EDU.STUDENT, answerArray[0], answerArray[1], answerArray[2], answerArray[3], answerArray[4]);
            session.setCurrentUser(student);
            return student;
        } else if (session.getEducation() == AppUser.EDU.FACULTY) {
            AppUser faculty = new AppUser(AppUser.EDU.FACULTY, answerArray[0], answerArray[1], answerArray[2], answerArray[3], answerArray[4]);
            session.setCurrentUser(faculty);
            return faculty;
        } else {
            logger.debug("getEducation() isn't returning student or faculty");
            return null;
        }
    }

    public void register(AppUser user){
        repo.save(user);
    }
    //calls the appropriate repo method, depending on the newOrUpdate argument
    public void update(AppUser beforeUpdate, AppUser user) {

            repo.update(user);

            if(user.getEdu().equals("STUDENT")){
                try {
                    List<Course> courses = courseService.getCourses(beforeUpdate);
                    for (Course course : courses) {
                        courseService.dropCourse(course.getCourseTag(), session);
                        session.setCurrentUser(user);
                        courseService.registerStudent(course.getCourseTag(), session);
                    }
                }catch(NullPointerException npe){
                    logger.info("UPDATE(): Student update doesn't require course edit. Student not registered for courses.");
                } finally {session.setCurrentUser(user);}
            } else if(user.getEdu().equals("FACULTY")){
                try {
                    List<Course> courses = courseService.getCourses(beforeUpdate);
                    session.setCurrentUser(user);
                    for (Course course : courses) {
                        course.setInstructor(user.getFirstName() + " " + user.getLastName());
                        course.setInstructorId(user.getId());
                        courseService.registerCourse(course, "update");
                    }
                }catch(NullPointerException npe){
                    logger.info("UPDATE: Faculty update doesn't require course edit. Faculty has no courses created.");
                }
            }
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
