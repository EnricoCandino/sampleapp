package it.enricocandino.sample.service.robospice.request.user;

import it.enricocandino.sample.model.User;
import it.enricocandino.sample.service.model.Account;
import it.enricocandino.sample.service.robospice.AbstractSpiceRequest;
import it.enricocandino.sample.service.robospice.BaseSpiceRequestListener;
import it.enricocandino.sample.service.robospice.service.UserService;
import it.enricocandino.sample.service.robospice.response.AccountResponse;

/**
 * Created by enrico on 20/02/15.
 */
public class SpiceLoginRequest extends AbstractSpiceRequest<User, AccountResponse, UserService> {

    private String user;
    private String password;

    public SpiceLoginRequest(String user, String password) {
        super(AccountResponse.class, UserService.class);
        this.user = user;
        this.password = password;

        this.listener = new MyListener();
    }

    @Override
    public AccountResponse loadDataFromNetwork() throws Exception {
        return getService().login(user, password);
    }

    @Override
    public BaseSpiceRequestListener<User, AccountResponse> getListener() {
        return listener;
    }

    private class MyListener extends BaseSpiceRequestListener<User, AccountResponse> {

        @Override
        public User prepareResponse(AccountResponse serverResponse) {
            Account account = serverResponse.getObject();

            User user = new User();
            user.setAccountId(Long.valueOf(account.getId()));
            user.setUsername(account.getUser());
            user.setRole(User.ROLE.valueOf(account.getRole().toUpperCase()));

            return user;
        }
    }
}
