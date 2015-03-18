package it.enricocandino.sample.controller.activity;

import android.support.v7.app.ActionBarActivity;

import it.enricocandino.sample.service.robospice.APICacheSpiceManager;
import it.enricocandino.sample.service.robospice.RetrofitSpiceService;

/**
 * Created by enrico on 03/03/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private APICacheSpiceManager spiceManager = new APICacheSpiceManager(RetrofitSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected APICacheSpiceManager getSpiceManager() {
        return spiceManager;
    }
}
