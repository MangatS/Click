package com.example.sikandermangat.click.Model;

import java.io.Serializable;

public class UserClass implements Serializable{

    private String UserName;
    private String gender;
    private String email;
    private String UserId;

    public UserClass() {



    }

    public String getUserName() {

        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
