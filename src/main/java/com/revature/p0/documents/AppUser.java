package com.revature.p0.documents;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.util.HashMap;
import java.util.Objects;

public class AppUser {
    public enum EDU {STUDENT, FACULTY};
    private String edu;
    @JsonProperty("_id") private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private HashMap<String, String> properties = new HashMap<>();

    @JsonCreator
    public AppUser(
            @JacksonInject EDU edu,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password) {

        if(edu == EDU.STUDENT)
            this.edu = "student";
        if(edu == EDU.FACULTY)
            this.edu = "faculty";
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



    public HashMap<String, String> getProperties() {return properties;}


    public static Document toDocument(AppUser newUser){
        Document newUserDoc = new Document("edu", newUser.getEdu())
                .append("firstName", newUser.getFirstName())
                .append("lastName", newUser.getLastName())
                .append("email", newUser.getEmail())
                .append("username", newUser.getUsername())
                .append("password", newUser.getPassword());
        return newUserDoc;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id == appUser.id && edu == appUser.edu && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(email, appUser.email) && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, edu, firstName, lastName, email, username, password);
    }
}

