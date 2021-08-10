package com.revature.p0.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.util.HashMap;

public class AppUser {
    public enum EDU {
        STUDENT, FACULTY;
        public static EDU fromString(String name) {
            for (EDU medium : EDU.values()) {
                if (medium.toString().equals(name)) {return medium;}
            } return null;
        }
    }
    private String edu;
    @JsonProperty("_id") private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private HashMap<String, String> properties = new HashMap<>();

    public AppUser(){}

    public AppUser(
            EDU edu,
            String firstName,
            String lastName,
            String email,
            String username,
            String password) {

        this.edu = edu.toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        properties.put("firstname", firstName);
        properties.put("lastname", lastName);
        properties.put("email", email);
        properties.put("username", username);
        properties.put("password", password);
        properties.put("edu", this.edu);
    }

    public Document toDocument(){

        Document newUserDoc = new Document("edu", this.getEdu())
                .append("firstName", this.getFirstName())
                .append("lastName", this.getLastName())
                .append("email", this.getEmail())
                .append("username", this.getUsername())
                .append("password", this.getPassword());
        return newUserDoc;
    }

    public HashMap<String, String> getProperties() {return properties;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

