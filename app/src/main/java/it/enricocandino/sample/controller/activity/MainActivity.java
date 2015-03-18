package it.enricocandino.sample.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import it.enricocandino.sample.R;
import it.enricocandino.sample.controller.fragment.HomeFragment;
import it.enricocandino.sample.controller.fragment.MenuFragment;
import it.enricocandino.sample.model.AppMenuItem;
import it.enricocandino.sample.view.MenuListView;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        initDrawer();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, new HomeFragment(), HomeFragment.TAG);
        ft.replace(R.id.menu_content, new MenuFragment(), MenuFragment.TAG);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(MenuFragment.TAG);
        if(menuFragment != null) {
            menuFragment.setMenuListener(new MyMenuItemClickListener());
        }
    }

    private void initDrawer() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    /*
     * MenuItemClickListener
     */
    private class MyMenuItemClickListener implements MenuListView.MenuItemClickListener {

        @Override
        public void onMenuItemClick(AppMenuItem item) {
            Fragment fragment = null;
            String tag = null;

            switch (item) {
                case SEARCH:
                    fragment = new HomeFragment();
                    tag = HomeFragment.TAG;
                    break;
                case SETTINGS:
                    break;
            }

            if(fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_content, fragment, tag);
                ft.commit();
            }

            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }
}
