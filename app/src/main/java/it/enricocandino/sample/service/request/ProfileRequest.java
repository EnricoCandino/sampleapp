package it.enricocandino.sample.service.request;

import it.enricocandino.sample.model.User;
import it.enricocandino.sample.service.RemoteRequest;
import it.enricocandino.sample.service.robospice.APICacheSpiceManager;
import it.enricocandino.sample.service.robospice.AbstractSpiceRequest;
import it.enricocandino.sample.service.robospice.request.user.SpiceProfileRequest;
import it.enricocandino.sample.service.robospice.response.ProfileResponse;

/**
 * Created by enrico on 13/03/15.
 */
public class ProfileRequest extends RemoteRequest<User, ProfileResponse> {

    private User user;

    public ProfileRequest(APICacheSpiceManager apiCacheSpiceManager) {
        super(apiCacheSpiceManager);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public AbstractSpiceRequest<User, ProfileResponse, ?> getSpiceRequest() {
        return new SpiceProfileRequest(user);
    }
}
