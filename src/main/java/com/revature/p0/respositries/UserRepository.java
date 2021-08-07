package com.revature.p0.respositries;

import com.revature.p0.exceptions.DataSourceException;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.revature.p0.documents.Student;
import com.revature.p0.util.MongoClientFactory;


import java.util.ArrayList;
import java.util.List;


public class UserRepository implements CrudRepository{

    public Student findStudentByCredentials(String username, String password){

        try{
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase projectDatabase = mongoClient.getDatabase("Project Zero");
            MongoCollection<Document> studentsCollection = projectDatabase.getCollection("student");
            Document queryDoc = new Document("username", username).append("password", password);
            Document authStudentDoc = studentsCollection.find(queryDoc).first();

            if(authStudentDoc == null){
                return null;
            }
            String[] valuesArray = {authStudentDoc.get("firstName").toString(), authStudentDoc.get("lastName").toString(), authStudentDoc.get("email").toString(),
                    authStudentDoc.get("username").toString(), authStudentDoc.get("password").toString()};

            return new Student(valuesArray);

        } catch (NullPointerException npe){
            npe.printStackTrace(); //TODO log this
            throw new DataSourceException("An Exception occured during query.", npe);
        } catch (Exception e){
            e.printStackTrace(); //TODO log this
            throw new DataSourceException("An unknown exception occurred", e);
        }
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
