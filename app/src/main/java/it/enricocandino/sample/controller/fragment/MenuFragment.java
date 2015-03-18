package it.enricocandino.sample.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.enricocandino.sample.R;
import it.enricocandino.sample.controller.flow.LoginFlowController;
import it.enricocandino.sample.controller.manager.MenuManager;
import it.enricocandino.sample.controller.manager.UserManager;
import it.enricocandino.sample.model.User;
import it.enricocandino.sample.util.ImageLoader;
import it.enricocandino.sample.util.Log;
import it.enricocandino.sample.view.MenuListView;

/**
 * Created by enrico on 08/02/15.
 */
public class MenuFragment extends Fragment {

    public static final String TAG = "MenuFragment";
    public static final String LOG_TAG = "MenuFragment";

    private ImageView profileIV;
    private TextView usernameTV;
    private MenuListView menuListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(mLoginReceiver, new IntentFilter(LoginFlowController.LOGIN_INTENT));

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        usernameTV = (TextView) view.findViewById(R.id.username_tv);
        profileIV = (ImageView) view.findViewById(R.id.profile_image);
        menuListView = (MenuListView) view.findViewById(R.id.menu_listview);
        menuListView.setItems(MenuManager.Items.get());
    }

    public void setMenuListener(MenuListView.MenuItemClickListener listener) {
        menuListView.setListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLoginReceiver);
    }

    private void reloadMenu() {
        Log.d(LOG_TAG, "Reloading Menu");

        if (UserManager.INSTANCE.isLogged()) {
            User user = UserManager.INSTANCE.getUser();
            String profilePicUrl = user.getProfilePicUrl();
            if (profilePicUrl != null) {
                ImageLoader.loadImage(getActivity(), profilePicUrl, profileIV);
            }
        } else {
            usernameTV.setText(R.string.login);
        }
    }

    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isLogged = UserManager.INSTANCE.isLogged();
            Log.d(LOG_TAG, "Received login event. User logged: [" + isLogged + "]");
            reloadMenu();
        }
    };

}
