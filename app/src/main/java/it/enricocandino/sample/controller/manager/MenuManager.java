package it.enricocandino.sample.controller.manager;

import java.util.ArrayList;
import java.util.List;

import it.enricocandino.sample.model.AppMenuItem;

/**
 * Created by enrico on 22/02/15.
 */
public enum MenuManager {

    Items;

    public List<AppMenuItem> get() {
        List<AppMenuItem> items = new ArrayList<>();

        items.add(AppMenuItem.SEARCH);
        items.add(AppMenuItem.MAP);
        items.add(AppMenuItem.PROFILE);
        items.add(AppMenuItem.SETTINGS);

        return items;
    }

}
