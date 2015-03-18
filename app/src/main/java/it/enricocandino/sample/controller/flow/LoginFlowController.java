package it.enricocandino.sample.controller.flow;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import it.enricocandino.sample.controller.manager.UserManager;
import it.enricocandino.sample.model.User;
import it.enricocandino.sample.service.RemoteListener;
import it.enricocandino.sample.service.request.LoginRequest;
import it.enricocandino.sample.service.request.ProfileRequest;
import it.enricocandino.sample.service.robospice.APICacheSpiceManager;
import it.enricocandino.sample.service.robospice.response.ErrorResponse;

/**
 * Created by enrico on 20/02/15.
 */
public class LoginFlowController {

    private static final String LOG_TAG = "LoginFlowController";
    public static final String LOGIN_INTENT = "LOGIN_INTENT";
    public static final String LOGIN_INTENT_MESSAGE = "LOGIN_INTENT_MESSAGE";

    private Activity mActivity;
    private APICacheSpiceManager spiceManager;

    public LoginFlowController(Activity activity, APICacheSpiceManager spiceManager) {
        this.spiceManager = spiceManager;
        this.mActivity = activity;
    }

    public void login(String username, String password) {
        LoginRequest request = new LoginRequest(spiceManager);
        request.setUser(username);
        request.setPassword(password);
        request.setListener(new RemoteListener<User>() {
            @Override
            public void onSuccess(User user) {
                getProfile(user);
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Intent intent = new Intent(LOGIN_INTENT);
                intent.putExtra(LOGIN_INTENT_MESSAGE, errorResponse.getMessage());
                LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intent);
            }
        });
        request.execute();
    }

    private void getProfile(final User user) {
        ProfileRequest profileRequest = new ProfileRequest(spiceManager);
        profileRequest.setUser(user);
        profileRequest.setListener(new RemoteListener<User>() {
            @Override
            public void onSuccess(User userResponse) {
                UserManager.INSTANCE.setUser(userResponse);

                LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent(LOGIN_INTENT));
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                Intent intent = new Intent(LOGIN_INTENT);
                intent.putExtra(LOGIN_INTENT_MESSAGE, errorResponse.getMessage());
                LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intent);
            }
        });
        profileRequest.execute();
    }
}
