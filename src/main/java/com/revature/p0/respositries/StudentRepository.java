package com.revature.p0.respositries;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.p0.POJO.Student;
import org.bson.Document;

public class StudentRepository extends Repository<Student> {
    MongoCollection collection;


    public StudentRepository(){
        super("student", "/student");
        try {
            String uri = "mongodb://hiroshin:revature@ec2-user:27017";
            MongoClient client = MongoClients.create(uri);
            MongoDatabase database = client.getDatabase("ProjectZero");
            this.collection = database.getCollection("Students");
        }catch (MongoClientException e){
            //TODO include a logger here
            System.err.println("Cannot connect to MongoDB database.");
        }
    }
    @Override
    public boolean exists(String username){
        try{
            collection.find(new Document("username", username)) ;
        } catch(Exception e){
            return false;
        }

    }

    @Override
    public Student search(Student type) {
        return null;
    }

    @Override
    public void save(Student student) {
        Document doc = new Document()
                .append("edu", student.getEdu())
                .append("firstname", student.getFirstName())
                .append("lastname", student.getLastName())
                .append("email", student.getEmail())
                .append("username", student.getUsername())
                .append("password", student.getPassword());
        collection.insertOne(doc);


    }
}
