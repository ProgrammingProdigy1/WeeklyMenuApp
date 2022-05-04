package com.projects.DataManagement;

import android.content.Intent;

/*
This data type represents a standard user of the app. each instance of this
class has information that will be stored in the database of the application.
 */
public class User {

    //Each user has a user name and password, with which he can login to the app
    private String username, password;

    // The id property will be used to differentiate between
    // the user data stored in the database and the data of others
    private long id;

    // The following fields are the information that will be collected from each user in the application.
    // This information is essential for the proper functioning of the app features.
    private int age;
    private Gender gender;
    private double weight, height;
    private SportStatus status;

    public User(String username, String password, int age, Gender gender, double weight, double height, SportStatus status) {
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.status = status;
        this.id = -1;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public SportStatus getStatus() {
        return status;
    }

    public void setStatus(SportStatus status) {
        this.status = status;
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

    // The following enum represents the gender of the app users.
    // Such information is essential because it affects the recommendations
    // we give to the users.
    public enum Gender{
        MALE, FEMALE, OTHER
    }

    // The following enum represents the sport status of the app users.
    // Such information is essential because it affects the recommendations
    // we give to the users.
    public enum SportStatus{
        VERY_ACTIVE, ACTIVE, SEMI_ACTIVE, COUCH_POTATO
    }
}
