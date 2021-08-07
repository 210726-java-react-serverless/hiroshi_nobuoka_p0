package com.revature.p0.repositories;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.revature.p0.documents.AppUser;
import com.revature.p0.exceptions.DataSourceException;
import com.revature.p0.exceptions.NullFieldException;
import com.revature.p0.util.UserSession;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.p0.util.MongoClientFactory;


public class UserRepository implements CrudRepository<AppUser>{
    private UserSession session;

    public UserRepository(UserSession session){ this.session = session;}

    public AppUser findUserByCredentials(String username, String password){

        try{
            MongoCollection<Document> userCollection = chooseCollection();
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
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            Document queryDoc = userCollection.find(new BasicDBObject("username", username)).first();

            return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(), queryDoc.get("password").toString());
        } catch (NullPointerException npe){
            npe.printStackTrace();
            throw new DataSourceException("Database, collection, or username not found", npe);
        }
    }

    public AppUser findUserByEmail(String email) {
        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            Document queryDoc = userCollection.find(new BasicDBObject("email", email)).first();
            return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(), queryDoc.get("password").toString());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            throw new DataSourceException("Database or colletion not found", npe);
        }
    }

    public MongoCollection<Document> chooseCollection(MongoDatabase db){
        try {
            MongoCollection<Document> userCollection;

            //Refers to the active user's education to determine which collection to query
            if (session.getEducation().equals(AppUser.Edu.STUDENT))
                return userCollection = db.getCollection("student");
            else if (session.getEducation().equals(AppUser.Edu.FACULTY))
                return userCollection = db.getCollection("faculty");
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
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            Document queryDoc = userCollection.find(new BasicDBObject("_id", id)).first();//TODO key may be incorrect, make sure to test
            return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(), queryDoc.get("password").toString());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            throw new DataSourceException("Database or colletion not found", npe);
        }
    }

    @Override
    public void save(AppUser newUser) {
        try {
            MongoCollection<Document> userCollection = chooseCollection();
            Document newUserDoc = new Document("firstName", newUser.getFirstName())
                    .append("lastName", newUser.getLastName())
                    .append("email", newUser.getEmail())
                    .append("username", newUser.getUsername())
                    .append("password", newUser.getPassword());

            newUser.setId(newUserDoc.get("_id").toString());

        } catch (Exception e) {
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    @Override
    public void update(AppUser user) {
        MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
        MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");
        MongoCollection<Document> userCollection = chooseCollection(projectDb);



    }

    @Override
    public void deleteById(int id) {
        MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
        MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");
        MongoCollection<Document> userCollection = chooseCollection(projectDb);

        userCollection.deleteOne(Filters.eq("_id", id));
        System.out.println("Document deleted successfully.");
    }
}
