package it.enricocandino.sample.controller.fragment;

import android.support.v4.app.Fragment;

import it.enricocandino.sample.service.robospice.APICacheSpiceManager;
import it.enricocandino.sample.service.robospice.RetrofitSpiceService;

/**
 * Created by enrico on 03/03/15.
 */
public class BaseFragment extends Fragment {

    private APICacheSpiceManager spiceManager = new APICacheSpiceManager(RetrofitSpiceService.class);

    @Override
    public void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    @Override
    public void onStop() {
        if(spiceManager.isStarted())
            spiceManager.shouldStop();
        super.onStop();
    }

    protected APICacheSpiceManager getSpiceManager() {
        return spiceManager;
    }
}
