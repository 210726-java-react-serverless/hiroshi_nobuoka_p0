package com.revature.p0.respositries;

import com.mongodb.BasicDBObject;
import com.mongodb.lang.NonNull;
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

            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDb = mongoClient.getDatabase("Project Zero");
            if(projectDb == null)
                throw new DataSourceException("Project not found.", new NullPointerException());
            MongoCollection<Document> userCollection = chooseCollection(projectDb);

            Document queryDoc = userCollection.find(new BasicDBObject("username", username)).first();
            if(queryDoc == null)
                throw new DataSourceException("Couldn't find username in collection", new NullPointerException());

            return new AppUser(session.getEducation(), queryDoc.get("firstName").toString(), queryDoc.get("lastName").toString(), queryDoc.get("email").toString(),
                    queryDoc.get("username").toString(), queryDoc.get("password").toString());

    }

    public AppUser findUserByEmail(String email){
        return null;
    }

    @NonNull
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
        return null;
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
    public boolean update(AppUser user) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
