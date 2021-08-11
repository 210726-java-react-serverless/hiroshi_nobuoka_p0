package com.revature.p0.repositories;


import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.DataSourceException;
import com.revature.p0.exceptions.DocumentNotFoundException;
import com.revature.p0.util.UserSession;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.p0.util.MongoClientFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.bson.types.ObjectId;

public class UserRepository implements CrudRepository<AppUser>{
    static final Logger logger = LogManager.getLogger(UserRepository.class);
    private UserSession session;
    private MongoClient mongoClient= MongoClientFactory.getInstance().getConnection();
    private MongoDatabase projectDb= mongoClient.getDatabase("ProjectZero");

    public UserRepository(UserSession session){
        this.session = session;
    }

    //implementation for login validation
    public AppUser findUserByCredentials(String username, String password){
        try{
            MongoCollection<Document> userCollection = chooseCollection(projectDb);
            Document queryDoc = new Document("username", username).append("password", password);
            Document authStudentDoc = userCollection.find(queryDoc).first();

            if(authStudentDoc == null){
                return null;
            }
            AppUser user = new AppUser(session.getEducation(), authStudentDoc.get("firstName").toString(), authStudentDoc.get("lastName").toString(), authStudentDoc.get("email").toString(),
                    authStudentDoc.get("username").toString(), authStudentDoc.get("password").toString());
            user.setId(authStudentDoc.get("_id").toString());
            return user;

        } catch (NullPointerException npe){
            npe.printStackTrace();
            throw new DataSourceException("An Exception occurred during query.", npe);
        } catch (Exception e){
            e.printStackTrace();
            throw new DataSourceException("An unknown exception occurred", e);
        }
    }

    //checks to see if username already exists in repo
    public AppUser findUserByUsername(String username){
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            Document queryDoc = userCollection.find(new BasicDBObject("username", username)).first();
            AppUser queriedUser = new AppUser(AppUser.EDU.fromString(queryDoc.get("edu").toString()),
                    queryDoc.get("_id").toString(),
                    queryDoc.get("firstName").toString(),
                    queryDoc.get("lastName").toString(),
                    queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(),
                    queryDoc.get("password").toString());
            return queriedUser;

        } catch (NullPointerException npe){
            logger.info("Username not found");
            return null;
        }
    }
    //checks if email exists in repo
    public AppUser findUserByEmail(String email) {
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            BasicDBObject name = new BasicDBObject("email", email);
            Document queryDoc = userCollection.find(name).first();

            AppUser queriedUser= new AppUser(AppUser.EDU.fromString(queryDoc.get("edu").toString()),
                    queryDoc.get("_id").toString(),
                    queryDoc.get("firstName").toString(),
                    queryDoc.get("lastName").toString(),
                    queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(),
                    queryDoc.get("password").toString());
            return queriedUser;


        } catch (NullPointerException npe) {
            logger.info("email address not found in database");
            return null;
        }
    }
    //Refers to the active user's education (i.e. student vs faculty) to determine which collection to query
    public MongoCollection<Document> chooseCollection(MongoDatabase db){
        try {
            if (session.getEducation().equals(AppUser.EDU.STUDENT)){
                logger.info("Student collection accessed");
                return db.getCollection("student");
            }
            else if (session.getEducation().equals(AppUser.EDU.FACULTY)) {
                logger.info("Faculty collection accessed");
                return db.getCollection("faculty");
            }
        } catch (NullPointerException npe){
            npe.printStackTrace();
        }
        return null;
    }

    //my Course model requires an AppUser(i.e. faculty) instance to be passed as an argument for its constructor. Used in most cases where a course needs to be made.
    public static AppUser findInstructorById(String id) throws NullPointerException {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDb = mongoClient.getDatabase("ProjectZero");
            MongoCollection<Document> userCollection = projectDb.getCollection("faculty");

            ObjectId workingId = new ObjectId(id);
            Document queryDoc = userCollection.find(new BasicDBObject("_id", workingId)).first();

            AppUser queriedUser= new AppUser(AppUser.EDU.fromString(queryDoc.get("edu").toString()),
                    queryDoc.get("_id").toString(),
                    queryDoc.get("firstName").toString(),
                    queryDoc.get("lastName").toString(),
                    queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(),
                    queryDoc.get("password").toString());
            return queriedUser;

    }

    /**
     * Checks to make sure the user we're trying to persist doesn't already exist in the repo. Then it converts the user to a
     * document, saves it to the repo, and gives the user the database-generated ID using a setter.
     */
    @Override
    public void save(AppUser newUser) {
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            Document queryDoc = userCollection.find(new BasicDBObject("_id", newUser.getId())).first();
            if(queryDoc != null) {
                throw new RuntimeException(newUser.getId()+" already exists in "+userCollection.toString());
            }

            Document newUserDoc = newUser.toDocument();
            userCollection.insertOne(newUserDoc);


            Document getIDDoc = userCollection.find(new BasicDBObject("username", newUser.getUsername())).first();
            newUser.setId(getIDDoc.get("_id").toString());
            logger.info("username "+newUser.getUsername()+" saved to "+userCollection.getNamespace()+" with id "+newUser.getId());


        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException("An unexpected exception occurred.", e);
            }
        }
    //Deletes the user persistence created prior to changes being made, then persists the user with the changes applied
    @Override
    public void update(AppUser updatedUser) {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);
            logger.info(updatedUser.getId());
            ObjectId id = new ObjectId(updatedUser.getId());

            Document queryDoc = userCollection.find(new BasicDBObject("_id", id)).first();

            if(queryDoc == null) {
                throw new DocumentNotFoundException();
            }
            userCollection.deleteOne(queryDoc);

            Document newUserDoc = updatedUser.toDocument();
            logger.debug(newUserDoc.toJson());
            userCollection.insertOne(newUserDoc);
            updatedUser.setId(newUserDoc.get("_id").toString());
    }

    @Override
    public void delete(String username) {
        MongoCollection<Document> userCollection = chooseCollection(projectDb);

        userCollection.deleteOne(Filters.eq("username", username));
        System.out.println("Document deleted successfully.");
    }


}
