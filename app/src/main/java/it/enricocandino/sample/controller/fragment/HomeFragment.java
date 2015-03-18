package it.enricocandino.sample.controller.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.enricocandino.sample.R;
import it.enricocandino.sample.controller.flow.LoginFlowController;
import it.enricocandino.sample.controller.manager.UserManager;
import it.enricocandino.sample.util.Log;
import it.enricocandino.sample.view.LoginView;

/**
 * Created by enrico on 08/02/15.
 */
public class HomeFragment extends BaseFragment {

    public static final String TAG = "HomeFragment";
    public static final String LOG_TAG = "HomeFragment";

    private LoginFlowController loginFlowController;
    private LoginView loginView;

    // just a quick fix for this sample
    private String mPendingMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(mLoginReceiver, new IntentFilter(LoginFlowController.LOGIN_INTENT));

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        loginView = (LoginView) view.findViewById(R.id.loginView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginFlowController = new LoginFlowController(getActivity(), getSpiceManager());
        loginView.setListener(new LoginViewListener());
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLoginReceiver);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(mPendingMessage != null) {
            Toast.makeText(activity, mPendingMessage, Toast.LENGTH_SHORT).show();
            mPendingMessage = null;
        }
    }

    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            loginView.showLoading(false);
            boolean isLogged = UserManager.INSTANCE.isLogged();
            Log.d(LOG_TAG, "Received login event. User logged: [" + isLogged + "]");

            String errorMessage = intent.getStringExtra(LoginFlowController.LOGIN_INTENT_MESSAGE);
            if(errorMessage != null) {
                Activity activity = getActivity();
                if(activity != null) {
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    mPendingMessage = errorMessage;
                }
            }
        }
    };

    /**
     * Handle clicks from the LoginView
     */
    private class LoginViewListener implements LoginView.LoginViewClickListener {

        @Override
        public void onLoginClick(String username, String password) {
            loginFlowController.login(username, password);
            loginView.showLoading(true);
        }
    }
}
