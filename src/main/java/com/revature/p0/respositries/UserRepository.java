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


public class UserRepository implements CrudRepository{
    private UserSession session;

    public UserRepository(UserSession session){ this.session = session;}

    public AppUser findUserByCredentials(String username, String password){

        try{
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDatabase = mongoClient.getDatabase("Project Zero");
            MongoCollection<Document> userCollection;

            //Refers to the active user's education to determine which collection to query
            if(session.getEducation().equals(AppUser.Edu.STUDENT))
                userCollection = projectDatabase.getCollection("student");
            else if(session.getEducation().equals(AppUser.Edu.FACULTY))
                userCollection = projectDatabase.getCollection("faculty");
            else
                throw new NullFieldException("AppUser's education is not provided.");

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
        
    }

    public AppUser findUserByEmail(String email){

    }


    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void save(Object type) {

    }

    @Override
    public boolean update(Object updatedResource) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
