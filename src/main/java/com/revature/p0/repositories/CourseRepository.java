package com.revature.p0.repositories;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import com.revature.p0.documents.AppUser;
import com.revature.p0.documents.Course;
import com.revature.p0.exceptions.DataSourceException;

import com.revature.p0.exceptions.DocumentNotFoundException;
import com.revature.p0.util.MongoClientFactory;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;



public class CourseRepository implements CrudRepository<Course>{
    static final Logger logger = LogManager.getLogger(UserRepository.class);
    private UserSession session;
    MongoClient mongoClient= MongoClientFactory.getInstance().getConnection();
    MongoDatabase projectDb = mongoClient.getDatabase("ProjectZero");
    MongoCollection<Document> userCollection = projectDb.getCollection("courses");

    public CourseRepository(UserSession session){this.session = session;}

    //primary means of course query for checking course availability to prevent duplication, and for student registration
    public Course findCourseByTag(String tag) throws NullPointerException{

            Document queryDoc = userCollection.find( new BasicDBObject("coursetag", tag)).first();
            AppUser instructor = UserRepository.findInstructorById(queryDoc.get("instructorId").toString());

            Course queriedCourse= new Course(queryDoc.get("_id").toString(),
                    queryDoc.get("coursetag").toString(),
                    queryDoc.get("coursename").toString(),
                    instructor, queryDoc.getList("enrolled", String.class));
            logger.info("Course found by findCourseByTag: "+queriedCourse);
            return queriedCourse;
    }

    @Override
    public void save(Course newCourse) {
        try {
            //Check to make sure course tag does not already exist in database.
            Document queryDoc = userCollection.find(new BasicDBObject("_id", newCourse.getCourseId())).first();
            if(queryDoc != null) {
                throw new RuntimeException(newCourse.getCourseId()+" already exists in "+userCollection.toString());
            }
            //Create course document and save to database.
            Document newCourseDoc = newCourse.toDocument();
            userCollection.insertOne(newCourseDoc);

            //Query the doc we just saved and obtain the course ID
            Document getIDDoc = userCollection.find(new BasicDBObject("coursetag", newCourse.getCourseTag())).first();
            newCourse.setCourseId(getIDDoc.get("_id").toString());
            logger.info("course "+newCourse.getCourseTag()+" saved to "+userCollection.getNamespace()+" with id "+newCourse.getCourseId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }
    @Override
    public void delete(String courseTag) {
        try {
            Course queryCourse = findCourseByTag(courseTag);
            logger.info("Found course "+queryCourse.getCourseTag()+" for delete() call");

            //making sure the instructor requesting course removal is the instructor for that course
            if (queryCourse.getInstructorId().equals(session.getCurrentUser().getId())) {
                userCollection.deleteOne(queryCourse.toDocument());
                System.out.println("Course " + courseTag + "removed, successfully.");
            } else
                System.out.println("Course is registered under "+session.getCurrentUser().getUsername()+". Permission to remove course, denied.");
        }catch (NullPointerException npe){
            npe.printStackTrace();
            System.out.println("Course tag not found");
        }
    }

    public FindIterable<Document> getAllCourses() {
        //try block is two lines to enable catch block to work.
        try {
            FindIterable<Document> courses = userCollection.find();
            return courses;
        }catch (NullPointerException npe){
            logger.debug(".GETALLCOURSES() returned null.");
            return null;
        }
    }

    //Deletes the user document made prior to update. Creates a new user document with updated fields.
    @Override
    public void update(Course updatedCourse) {
        ObjectId id = new ObjectId(updatedCourse.getCourseId());

        Document queryDoc = userCollection.find(new BasicDBObject("_id", id)).first();
        logger.info("UPDATE queryDoc contains " +queryDoc);
        if(queryDoc == null) {
            logger.debug("Course document "+ updatedCourse.getCourseTag()+ " not found for updating.");
            throw new DocumentNotFoundException();}

        userCollection.deleteOne(queryDoc);

        Document newCourseDoc = updatedCourse.toDocument();
        userCollection.insertOne(newCourseDoc);
        updatedCourse.setCourseId(newCourseDoc.get("_id").toString());
    }
    //Find course by tag and add student to list of enrolled students, which is a field specific to each course
    public void registerStudentForCourse(String tag, UserSession session)throws NullPointerException{
        Course course = this.findCourseByTag(tag);
        String username = session.getCurrentUser().getUsername();
        if(!course.getEnrolled().contains(username)) {
            course.getEnrolled().add(username);
            System.out.println("Registration successful! You are now registered to "+course.getCourseTag()+".\n");

            this.update(course);
        } else {
            System.out.println("You're already registered for the course!\n");
            return;
        }
    }

    public void dropStudentFromCourse(String tag, UserSession session){
        //Find course and remove student from list of enrolled students
        Course course = this.findCourseByTag(tag);
        String username = session.getCurrentUser().getUsername();
        if(course.getEnrolled().contains(username)) {
            logger.info("Student " + username + " dropped from course " + course.getCourseTag());
            course.getEnrolled().remove(username);
            System.out.println("Course drop successful!");
        }else {
            System.out.println("Failed to drop course. You must be registered for a course in order to drop it.");
            return;
        }

        this.update(course);
    }


}
