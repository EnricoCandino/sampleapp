package it.enricocandino.sample.model;

/**
 * Created by enrico on 22/02/15.
 */
public enum AppMenuItem {

    SEARCH("Search"),
    MAP("Map"),
    PROFILE("Profile"),
    SETTINGS("Settings");

    private String name;

    AppMenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
