package com.example.asus.FYP;

import android.app.Application;

/**
 * Created by ASUS on 18/2/2018.
 */

public class GlobalVir extends Application {


    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public void setServerHostName(String serverHostName) {
        this.serverHostName = serverHostName;
    }

    public int getTesting() {
        return testing;
    }

    public void setTesting(int testing) {
        this.testing = testing;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String serverHostName = "http://4b7baa16.ngrok.io";
    public int testing = 0;
    public String sendIR;
    public String userName;
    public String userEmail;
    public int userID;
    public String password;
    public int phoneNumber;
    public int EAID;
    public String chatbotName;
    public String EA_name;
}
