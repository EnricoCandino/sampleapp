package it.enricocandino.sample.service.robospice.response;

import it.enricocandino.sample.service.model.Profile;

/**
 * Created by enrico on 20/02/15.
 */
public class ProfileResponse extends RemoteResponse {

    private Profile object;

    public Profile getObject() {
        return object;
    }

    public void setObject(Profile object) {
        this.object = object;
    }
}
