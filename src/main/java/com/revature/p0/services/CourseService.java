package com.revature.p0.services;

import com.revature.p0.documents.AppUser;
import com.revature.p0.documents.Course;
import com.revature.p0.repositories.CourseRepository;
import com.revature.p0.repositories.UserRepository;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CourseService {
    static final Logger logger = LogManager.getLogger(UserService.class);
    private final CourseRepository repo;
    private UserSession session;

    public CourseService(CourseRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

    public Course createCourse(String[] answerArray) throws ArrayIndexOutOfBoundsException{
        Course course = new Course(answerArray[0], answerArray[1], session.getCurrentUser().toString());
        return course;
    }
    public void registerCourse(Course course, String newOrUpdate) {
        if (newOrUpdate.equals("new"))
            repo.save(course);
        else if(newOrUpdate.equals("update"))
            repo.update(course);
        else
            throw new IllegalArgumentException("Argument for register() method must be 'new' or 'update'");
    }

    public void removeCourse(String courseTag){
        repo.delete(courseTag);
    }

    public boolean courseTagAvailable(String tag){
        if(repo.findCourseByTag(tag) == null)
            return true;
        return false;
    }


}
