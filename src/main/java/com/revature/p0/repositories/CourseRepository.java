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

import java.util.List;

public class CourseRepository implements CrudRepository<Course>{
    static final Logger logger = LogManager.getLogger(UserRepository.class);
    private UserSession session;
    MongoClient mongoClient;
    MongoDatabase projectDb;

    public CourseRepository(UserSession session){
        MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
        MongoDatabase projectDb = mongoClient.getDatabase("ProjectZero");
        this.mongoClient = mongoClient;
        this.projectDb = projectDb;
        this.session = session;
    }

    public Course findCourseByTag(String tag) throws NullPointerException{

            MongoCollection<Document> userCollection = projectDb.getCollection("courses");

            Document queryDoc = userCollection.find( new BasicDBObject("coursetag", tag)).first();
            AppUser instructor = UserRepository.findInstructorById(queryDoc.get("instructorId").toString());

            Course queriedCourse= new Course(queryDoc.get("_id").toString(),
                    queryDoc.get("coursetag").toString(),
                    queryDoc.get("coursename").toString(),
                    instructor);
            return queriedCourse;
    }


    @Override
    public void save(Course newCourse) {
        try {
            MongoCollection<Document> userCollection = projectDb.getCollection("courses");
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
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }
    @Override
    public void delete(String courseTag) {
        try {
            MongoCollection<Document> userCollection = projectDb.getCollection("courses");
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

    public FindIterable<Document> getAllCourses() throws NullPointerException{
            MongoCollection<Document> userCollection = projectDb.getCollection("courses");
            return userCollection.find();
    }
    @Override
    public void update(Course updatedCourse) {
        MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
        MongoDatabase projectDb = mongoClient.getDatabase("ProjectZero");
        MongoCollection<Document> userCollection = projectDb.getCollection("courses");

        ObjectId id = new ObjectId(updatedCourse.getCourseId());
        //Delete the user document made prior to update.
        Document queryDoc = userCollection.find(new BasicDBObject("_id", id)).first();

        if(queryDoc == null) {

            throw new DocumentNotFoundException();
        }
        userCollection.deleteOne(queryDoc);
        //Create a new user document with updated fields.
        Document newCourseDoc = updatedCourse.toDocument();
        userCollection.insertOne(newCourseDoc);
        updatedCourse.setCourseId(newCourseDoc.get("_id").toString());
    }



}
