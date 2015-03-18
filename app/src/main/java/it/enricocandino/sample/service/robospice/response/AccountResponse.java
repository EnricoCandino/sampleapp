package it.enricocandino.sample.service.robospice.response;

import it.enricocandino.sample.service.model.Account;

/**
 * Created by enrico on 20/02/15.
 */
public class AccountResponse extends RemoteResponse {

    private Account object;

    public Account getObject() {
        return object;
    }

    public void setObject(Account object) {
        this.object = object;
    }

}
