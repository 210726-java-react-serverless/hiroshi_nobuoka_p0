package com.revature.p0.repositories;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.p0.documents.AppUser;
import com.revature.p0.documents.Course;
import com.revature.p0.exceptions.DataSourceException;
import com.revature.p0.util.MongoClientFactory;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

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

    public Course findCourseByTag(String tag) {
        try {
            MongoCollection<Document> userCollection = projectDb.getCollection("courses");


            Document queryDoc = userCollection.find( new BasicDBObject("tag", tag)).first();

            Course queriedCourse= new Course(queryDoc.get("tag").toString(), queryDoc.get("coursename").toString(),queryDoc.get("instructor").toString());
            return queriedCourse;

        } catch (NullPointerException npe) {
            logger.info("Course tag not found in database");
            return null;
        }
    }

    @Override
    public Course findById(String id) {
        return null;
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
    public void update(Course updatedCourse) {

    }

    @Override
    public void deleteById(int id) {

    }
}
