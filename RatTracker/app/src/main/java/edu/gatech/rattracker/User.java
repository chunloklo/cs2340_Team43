package edu.gatech.rattracker;

/**
 * Created by Chunlok Lo on 10/1/2017.
 */

public class User {
    public String name;
    public String password;
    public boolean isAdmin;

    public User(String name, String password, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    //null user for Firebase casting when retrieving
    public User() {
        name = null;
        password = null;
        isAdmin = false;
    }

    //static singleton User for current user
    private static User currentUser = null;

    public static void setUser(User newUser) {
        currentUser = newUser;
    }
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearUser() {
        currentUser = null;
    }
}