package com.revature.p0.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.DataSourceException;
import com.revature.p0.exceptions.DocumentNotFoundException;
import com.revature.p0.exceptions.NullFieldException;
import com.revature.p0.util.UserSession;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.p0.util.MongoClientFactory;
import org.mongojack.JacksonMongoCollection;

import javax.sql.DataSource;


public class UserRepository implements CrudRepository<AppUser>{
    private UserSession session;
    MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
    MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");

    public UserRepository(UserSession session){ this.session = session;}

    public AppUser findUserByCredentials(String username, String password){

        try{
            MongoCollection<Document> userCollection = chooseCollection(projectDb);
            Document queryDoc = new Document("username", username).append("password", password);
            Document authStudentDoc = userCollection.find(queryDoc).first();

            if(authStudentDoc == null){
                return null;
            }

            return new AppUser(session.getEducation(), authStudentDoc.get("firstName").toString(), authStudentDoc.get("lastName").toString(), authStudentDoc.get("email").toString(),
                    authStudentDoc.get("username").toString(), authStudentDoc.get("password").toString());

        } catch (NullPointerException npe){
            npe.printStackTrace(); //TODO log this
            throw new DataSourceException("An Exception occurred during query.", npe);
        } catch (Exception e){
            e.printStackTrace(); //TODO log this
            throw new DataSourceException("An unknown exception occurred", e);
        }
    }


    public AppUser findUserByUsername(String username){
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            ObjectMapper mapper = new ObjectMapper();
            AppUser queriedUser;
            Document queryDoc = userCollection.find(new BasicDBObject("username", username)).first();

            return queriedUser = mapper.readValue(queryDoc.toJson(), AppUser.class);
            //return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                   // queryDoc.get("username").toString(), queryDoc.get("password").toString());

        } catch (NullPointerException npe){
            npe.printStackTrace();
            throw new DataSourceException("Database, collection, or username not found", npe);
        } catch (JsonMappingException jme){
            jme.printStackTrace();
            throw new DataSourceException("Json mapping exception", jme);
        } catch (JsonProcessingException jpe){
            jpe.printStackTrace();;
            throw new DataSourceException("Json processing exception", jpe);
        }

    }

    public AppUser findUserByEmail(String email) {
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            ObjectMapper mapper = new ObjectMapper();
            AppUser queriedUser;
            Document queryDoc = userCollection.find(new BasicDBObject("email", email)).first();

            return queriedUser = mapper.readValue(queryDoc.toJson(), AppUser.class);
            //return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                    //queryDoc.get("username").toString(), queryDoc.get("password").toString()); //TODO delete if Jackson works

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            throw new DataSourceException("Database or colletion not found", npe);
        } catch (JsonMappingException jme){
        jme.printStackTrace();
        throw new DataSourceException("Json mapping exception", jme);
        } catch (JsonProcessingException jpe){
        jpe.printStackTrace();;
        throw new DataSourceException("Json processing exception", jpe);
        }
    }

    public MongoCollection<Document> chooseCollection(MongoDatabase db){
        try {
            //Refers to the active user's education to determine which collection to query
            if (session.getEducation().equals(AppUser.Edu.STUDENT))
                return db.getCollection("student");
            else if (session.getEducation().equals(AppUser.Edu.FACULTY))
                return db.getCollection("faculty");
            else
                throw new NullFieldException("AppUser's education is not provided.");
        } catch (NullPointerException npe){
            npe.printStackTrace();//TODO log, include message that database returns null
        }
        return null;
    }

    @Override
    public AppUser findById(String id) {
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            ObjectMapper mapper = new ObjectMapper();
            AppUser queriedUser;
            Document queryDoc = userCollection.find(new BasicDBObject("_id", id)).first();//TODO key may be incorrect, make sure to test

            return queriedUser = mapper.readValue(queryDoc.toJson(), AppUser.class);
            //return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                    //queryDoc.get("username").toString(), queryDoc.get("password").toString());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            throw new DataSourceException("Database or collection not found", npe);
        } catch (JsonMappingException jme){
            jme.printStackTrace();
            throw new DataSourceException("Json mapping exception", jme);
        } catch (JsonProcessingException jpe){
            jpe.printStackTrace();;
            throw new DataSourceException("Json processing exception", jpe);
        }
    }

    @Override
    public void save(AppUser newUser) {
        try {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);
            //Check to make sure user does not already exist in database.
            Document queryDoc = userCollection.find(new BasicDBObject("_id", newUser.getId())).first();//TODO log content of this doc
            if(queryDoc != null)
                throw new RuntimeException("You tried to register a user that already exists.");
            //Create user document and save to database.
            Document newUserDoc = AppUser.toDocument(newUser);
            userCollection.insertOne(newUserDoc);
            newUser.setId(newUserDoc.get("_id").toString());

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
            }
        }

    @Override
    public void update(AppUser updatedUser) {
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            //Delete the user document made prior to update.
            Document queryDoc = userCollection.find(new BasicDBObject("_id", updatedUser.getId())).first();//TODO log content of this doc
            if(queryDoc == null)
                throw new DocumentNotFoundException();
            userCollection.deleteOne(queryDoc);
            //Create a new user document with updated fields.
            Document newUserDoc = AppUser.toDocument(updatedUser);
            userCollection.insertOne(newUserDoc);
            updatedUser.setId(newUserDoc.get("_id").toString());


    }

    @Override
    public void deleteById(int id) {
        MongoCollection<Document> userCollection = chooseCollection(projectDb);

        userCollection.deleteOne(Filters.eq("_id", id));
        System.out.println("Document deleted successfully.");
    }


}
