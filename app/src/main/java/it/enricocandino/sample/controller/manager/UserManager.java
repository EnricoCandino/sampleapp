package it.enricocandino.sample.controller.manager;

import it.enricocandino.sample.model.User;

/**
 * Created by enrico on 20/02/15.
 */
public enum UserManager {

    INSTANCE;

    private User user;

    public boolean isLogged() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
