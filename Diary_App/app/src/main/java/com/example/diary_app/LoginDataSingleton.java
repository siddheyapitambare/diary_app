package com.example.diary_app;

public class LoginDataSingleton {
    private static LoginDataSingleton instance;

    private String userUsername;
    private String userPassword;
    private String nameFromDB;
    private String emailFromDB;
    private String phoneFromDB;
    private String uniqueKey;

    private LoginDataSingleton() {
    }

    public static synchronized LoginDataSingleton getInstance() {
        if (instance == null) {
            instance = new LoginDataSingleton();
        }
        return instance;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNameFromDB() {
        return nameFromDB;
    }

    public void setNameFromDB(String nameFromDB) {
        this.nameFromDB = nameFromDB;
    }

    public String getEmailFromDB() {
        return emailFromDB;
    }

    public void setEmailFromDB(String emailFromDB) {
        this.emailFromDB = emailFromDB;
    }

    public String getPhoneFromDB() {
        return phoneFromDB;
    }

    public void setPhoneFromDB(String phoneFromDB) {
        this.phoneFromDB = phoneFromDB;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
