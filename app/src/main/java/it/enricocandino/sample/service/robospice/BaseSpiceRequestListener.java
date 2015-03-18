package it.enricocandino.sample.service.robospice;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import it.enricocandino.sample.service.RemoteListener;
import it.enricocandino.sample.service.robospice.response.ErrorResponse;
import it.enricocandino.sample.service.robospice.response.RemoteResponse;

/**
 * This BaseSpiceRequestListener check for any
 *
 *
 * Created by enrico on 13/03/15.
 */
public abstract class BaseSpiceRequestListener<V, K extends RemoteResponse> implements RequestListener<K> {

    protected RemoteListener<V> listener;

    public void setListener(RemoteListener<V> listener) {
        this.listener = listener;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        if(listener != null)
            listener.onError(prepareErrorResponse(spiceException));
    }

    @Override
    public void onRequestSuccess(K serverResponse) {
        if(validateRespone(serverResponse)) {
            V parsedResponse = prepareResponse(serverResponse);

            if (listener != null) {
                if (parsedResponse != null)
                    listener.onSuccess(parsedResponse);
                else
                    listener.onError(new ErrorResponse("Error while parsing response"));
            }
        }
    }

    /**
     * Override this method to do more logic on the not successful response (for example for handling different logic errors).
     * If the response is not valid it will be not parsed and the onError will be called
     *
     * @param serverResponse
     * @return true if the response is valid. False otherwise.
     */
    public boolean validateRespone(K serverResponse) {
        if(!serverResponse.isSuccess() && listener != null) {
            listener.onError(new ErrorResponse("Response is not successful"));
            return false;
        }
        return true;
    }

    /**
     * Implement this method in order to prepare the model object from the response
     *
     * @param serverResponse
     * @return
     */
    public abstract V prepareResponse(K serverResponse);

    public ErrorResponse prepareErrorResponse(SpiceException spiceException) {
        // TODO better error handling
        return new ErrorResponse(spiceException.getMessage());
    }
}
