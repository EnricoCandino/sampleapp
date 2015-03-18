package it.enricocandino.sample.service.robospice;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import it.enricocandino.sample.service.robospice.response.RemoteResponse;

/**
 * Created by enrico on 03/03/15.
 */
public abstract class AbstractSpiceRequest<V, T extends RemoteResponse, K> extends RetrofitSpiceRequest<T, K> {

    protected BaseSpiceRequestListener<V, T> listener;

    public AbstractSpiceRequest(Class<T> clazz, Class<K> retrofitedInterfaceClass) {
        super(clazz, retrofitedInterfaceClass);
    }

    public abstract BaseSpiceRequestListener<V, T> getListener();

    public String getCacheKey() {
        return null;
    }

    public long getCacheDuration() {
        return 0;
    }
}
