package com.revature.p0.respositries;

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
            MongoCollection<Document> userCollection = openProjectZero();
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
        return null;
    }

    public AppUser findUserByEmail(String email){
        return null;
    }

    public MongoCollection<Document> openProjectZero(){
        try {
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDatabase = mongoClient.getDatabase("Project Zero");
            MongoCollection<Document> userCollection;

            //Refers to the active user's education to determine which collection to query
            if (session.getEducation().equals(AppUser.Edu.STUDENT))
                return userCollection = projectDatabase.getCollection("student");
            else if (session.getEducation().equals(AppUser.Edu.FACULTY))
                return userCollection = projectDatabase.getCollection("faculty");
            else
                throw new NullFieldException("AppUser's education is not provided.");
        } catch (NullPointerException npe){
            npe.printStackTrace();//TODO log, include message that database returns null
        }
        return null;
    }

    @Override
    public AppUser findById(int id) {
        return null;
    }

    @Override
    public void save(AppUser newUser) {
        try {
            MongoCollection<Document> userCollection = openProjectZero();
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
    public boolean update(AppUser user) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
