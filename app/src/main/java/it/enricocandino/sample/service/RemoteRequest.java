package it.enricocandino.sample.service;

import it.enricocandino.sample.service.robospice.APICacheSpiceManager;
import it.enricocandino.sample.service.robospice.AbstractSpiceRequest;
import it.enricocandino.sample.service.robospice.BaseSpiceRequestListener;
import it.enricocandino.sample.service.robospice.response.RemoteResponse;

/**
 * Create a RemoteRequest passing as parameters the model object to be returned and the object returned from the service.
 * The object returned from the server must extends the RemoteResponse class.
 *
 * @param <V> the object returned from the listener
 * @param <K> the response returned from the service
 *
 * Created by enrico on 12/03/15.
 */
public abstract class RemoteRequest<V, K extends RemoteResponse> {

    private APICacheSpiceManager mAPICacheSpiceManager;
    private RemoteListener<V>  listener;

    public RemoteRequest(APICacheSpiceManager apiCacheSpiceManager) {
        mAPICacheSpiceManager = apiCacheSpiceManager;
    }

    /**
     * Set the listener used to return the object
     *
     * @param listener
     */
    public void setListener(RemoteListener<V> listener) {
        this.listener = listener;
    }

    /**
     * Execute the RemoteRequest. This will use an AbstractSpiceRequest that will parse the Response into the expected object
     */
    public void execute() {
        AbstractSpiceRequest<V, K, ?> request = getSpiceRequest();
        BaseSpiceRequestListener<V, K> spiceListener = request.getListener();
        spiceListener.setListener(listener);
        mAPICacheSpiceManager.fetch(request, spiceListener);
    }

    /**
     * Implement this method to return the concrete implementation used from the RemoteRequest to handle the request
     *
     * @return
     */
    public abstract AbstractSpiceRequest<V, K, ?> getSpiceRequest();
}
