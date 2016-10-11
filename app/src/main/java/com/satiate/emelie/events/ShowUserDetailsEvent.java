package com.satiate.emelie.events;

import com.satiate.emelie.models.User;

/**
 * Created by Rishabh Bhatia on 10/11/2016.
 */

public class ShowUserDetailsEvent {

    private User user;

    public ShowUserDetailsEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
