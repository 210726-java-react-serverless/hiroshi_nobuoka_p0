package com.revature.p0.documents;


public abstract class User {
    String edu;
    String firstName;
    String lastName;
    String email;
    String username;
    String password;

    public User(String[] userInfo) {
        this.edu = userInfo[0];
        this.firstName = userInfo[1];
        this.lastName = userInfo[2];
        this.email = userInfo[3];
        this.username = userInfo[4];
        this.password = userInfo[5];
    }

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

