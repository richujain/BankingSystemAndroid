package com.example.bankingsystemandroid;

public class LoginTimeDetails {
    private String id;

    private String loginTime;

    public LoginTimeDetails() {
    }

    public LoginTimeDetails(String id,String LoginTime ) {

        this.id=id;
        this.loginTime = LoginTime;
    }
    //getter and setter methods


    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id; }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String lTime) {this.loginTime = lTime; }
}
