package it.enricocandino.sample.service;

import it.enricocandino.sample.service.robospice.response.ErrorResponse;

/**
 * Created by enrico on 12/03/15.
 */
public interface RemoteListener<V> {

    /**
     * The model object returned from the RemoteRequest
     *
     * @param response
     */
    public void onSuccess(V response);

    /**
     * In case of error an ErrorResponse object will be returned from the RemoteRequest.
     * This can be a "logic" or network error.
     *
     * @param errorResponse
     */
    public void onError(ErrorResponse errorResponse);

}
