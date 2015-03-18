package it.enricocandino.sample.service.robospice;

import android.util.Base64;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import it.enricocandino.sample.service.robospice.service.UserService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by enrico on 03/03/15.
 */
public class RetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://myapi.com";

    @Override
    public void onCreate() {
        super.onCreate();

        addRetrofitInterface(UserService.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

    protected RestAdapter.Builder createRestAdapterBuilder() {
        return new RestAdapter.Builder().setEndpoint(getServerUrl()).setConverter(getConverter())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String string = "Basic " + Base64.encodeToString("user:password".getBytes(), Base64.NO_WRAP);
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", string);
                    }
                });
    }
}
