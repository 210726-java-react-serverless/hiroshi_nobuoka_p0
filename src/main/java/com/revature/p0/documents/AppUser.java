package com.revature.p0.documents;


import org.bson.Document;

import java.util.HashMap;
import java.util.Objects;

public class AppUser {
    public enum Edu {STUDENT, FACULTY};
    private String id;
    private Edu edu;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private HashMap<String, String> fieldDirectory = new HashMap<>();

    public AppUser(Edu edu, String firstName, String lastName, String email, String username, String password) {
        this.edu = edu;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        fieldDirectory.put("firstname", firstName);
        fieldDirectory.put("lastname", lastName);
        fieldDirectory.put("email", email);
        fieldDirectory.put("username", username);
        fieldDirectory.put("password", password);
    }

    public HashMap<String, String> getFieldDirectory() {return fieldDirectory;}

    public static Document toDocument(AppUser newUser){
        Document newUserDoc = new Document("firstName", newUser.getFirstName())
                .append("lastName", newUser.getLastName())
                .append("email", newUser.getEmail())
                .append("username", newUser.getUsername())
                .append("password", newUser.getPassword());
        return newUserDoc;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public Edu getEdu() {
        return edu;
    }

    public void setEdu(Edu edu) {
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

