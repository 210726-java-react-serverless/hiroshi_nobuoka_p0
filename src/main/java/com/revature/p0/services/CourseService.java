package com.revature.p0.services;


import com.mongodb.client.FindIterable;
import com.revature.p0.documents.AppUser;
import com.revature.p0.documents.Course;
import com.revature.p0.repositories.CourseRepository;

import com.revature.p0.repositories.UserRepository;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.mockito.internal.matchers.Null;


import java.util.ArrayList;
import java.util.List;

public class CourseService {
    static final Logger logger = LogManager.getLogger(UserService.class);
    private final CourseRepository repo;
    private UserSession session;
    QuestionFactory qFactory = QuestionFactory.getInstance();


    public CourseService(CourseRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

    public Course createCourse(String[] answerArray) throws ArrayIndexOutOfBoundsException{
        Course course = new Course(answerArray[0], answerArray[1], session.getCurrentUser());
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


    public List<Course> getCourses(AppUser user) throws NullPointerException {
        FindIterable<Document> docs = repo.getAllCourses();
        if (user.getEdu().equals("STUDENT")) {
            List<Course> courses = new ArrayList<>();
            for (Document doc : docs) {
                AppUser instructor = UserRepository.findInstructorById(doc.get("instructorId").toString());
                List<String> enrolled = doc.getList("enrolled", String.class);
                logger.info("GETCOURSES(): "+doc.get("coursetag")+ " have enrollees " +enrolled);
                if (enrolled.contains(user.getUsername())) {
                    Course course = new Course(doc.get("_id").toString(),
                            doc.get("coursetag").toString(),
                            doc.get("coursename").toString(),
                            instructor, enrolled);
                    courses.add(course);
                }
            } return courses;
        } else if (user.getEdu().equals("FACULTY")) {
            List<Course> courses = new ArrayList<>();
            for (Document doc : docs) {
                if (doc.get("instructorId").equals(user.getId())) {
                    Course course = new Course(doc.get("_id").toString(),
                            doc.get("coursetag").toString(),
                            doc.get("coursename").toString(),
                            user, doc.getList("enrolled", String.class));
                    courses.add(course);
                }
            } return courses;
        } else {
            logger.debug("user's education returns " + user.getEdu() + "for getCourses() method");
            return null;
        }
    }
    //Overloaded course
    public List<Course> getCourses(AppUser user, String all) throws NullPointerException {
            FindIterable<Document> docs = repo.getAllCourses(); //TODO sort
            List<Course> courses = new ArrayList<>();
            for(Document doc: docs){
                AppUser instructor = UserRepository.findInstructorById(doc.get("instructorId").toString());
                Course course = new Course(doc.get("_id").toString(),
                        doc.get("coursetag").toString(),
                        doc.get("coursename").toString(),
                        instructor, doc.getList("enrolled", String.class));
                courses.add(course);
            } return courses;
        }

    public void registerStudent(String tag, UserSession session){
        try {
            repo.registerStudentForCourse(tag, session);
        }catch(NullPointerException npe){
            logger.debug(".CourseService.registerStudent: Course document not found in database. Verification of its existence should have completed prior to method call.");
            System.out.println("Course registration failed. Sorry for the inconvenience.");
        }
    }

    public boolean courseTagAvailable(String tag){
        try {
            repo.findCourseByTag(tag);
            return false;
        }catch (NullPointerException npe){
            logger.info("Course tag "+tag+ " is available.");
            return true;
        }
    }
    public Course returnCourseByTag(String tag){
        try{
            return repo.findCourseByTag(tag);
        } catch (NullPointerException npe){
            System.out.println("Course tag not found. Please try again.");
            return null;
        }
    }

    public void dropCourse(String tag, UserSession session){
        try {
            repo.dropStudentFromCourse(tag, session);
        }catch(NullPointerException npe){
            logger.debug(".CourseService.registerStudent: Course document not found in database. Verification of its existence should have completed prior to method call.");
            System.out.println("Course drop failed. Sorry for the inconvenience.");
        }
    }


}
