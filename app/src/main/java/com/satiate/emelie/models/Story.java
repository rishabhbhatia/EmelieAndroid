package com.satiate.emelie.models;

/**
 * Created by Rishabh Bhatia on 10/15/2016.
 */

public class Story {

    private String title;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
