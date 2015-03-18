package it.enricocandino.sample.service.request;

import it.enricocandino.sample.model.User;
import it.enricocandino.sample.service.RemoteRequest;
import it.enricocandino.sample.service.robospice.APICacheSpiceManager;
import it.enricocandino.sample.service.robospice.AbstractSpiceRequest;
import it.enricocandino.sample.service.robospice.request.user.SpiceLoginRequest;
import it.enricocandino.sample.service.robospice.response.AccountResponse;

/**
 * Created by enrico on 13/03/15.
 */
public class LoginRequest extends RemoteRequest<User, AccountResponse> {

    private String user;
    private String password;

    public LoginRequest(APICacheSpiceManager apiCacheSpiceManager) {
        super(apiCacheSpiceManager);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public AbstractSpiceRequest<User, AccountResponse, ?> getSpiceRequest() {
        return new SpiceLoginRequest(user, password);
    }

}
