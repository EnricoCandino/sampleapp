package it.enricocandino.sample.service.robospice;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import it.enricocandino.sample.service.cache.APICache;
import it.enricocandino.sample.service.robospice.response.RemoteResponse;
import it.enricocandino.sample.util.Log;

/**
 * Created by enrico on 03/03/15.
 */
public class APICacheSpiceManager extends SpiceManager {

    private static final String LOG_TAG = "APICacheSpiceManager";

    // singleton memory cache, extension/wrapper of Android's LruCache
    private final APICache cache = APICache.getInstance();

    public APICacheSpiceManager(Class<? extends SpiceService> spiceServiceClass) {
        super(spiceServiceClass);
    }

    /*
     * Execute the request and return the result to the listener.
     * This will check if the request need to be cached or not, and will eventually cache the result.
     *
     */
    public <V, T extends RemoteResponse, R> void fetch(AbstractSpiceRequest<V, T, R> request, RequestListener<T> listener) {
        T result;

        String cacheKey = request.getCacheKey();
        long cacheDuration = request.getCacheDuration();

        if (cacheKey != null) {
            // TODO check which cache we should check

            // load from memory cache
            result = cache.get(APICache.CacheType.MEMORY, cacheKey);

            if (result != null) {
                Log.d(LOG_TAG, "Load request from L1 cache: " + request);
                listener.onRequestSuccess(result);

            } else {
                // store in memory cache
                listener = new CacheFillerListener<>(cache, cacheKey, cacheDuration, listener);

                // TODO load from disk cache

                if (result != null) {
                    Log.d(LOG_TAG, "Load request from L2 cache: " + request);
                    // send cached result immediately and return
                    listener.onRequestSuccess(result);

                } else {
                    Log.d(LOG_TAG, "Executing Spice request: " + request + " ON " + this);
                    super.execute(request, listener);
                }
            }
        } else {
            execute(request, listener);
        }
    }

    private class CacheFillerListener<T extends RemoteResponse> implements RequestListener<T> {

        private APICache cache;
        private String cacheKey;
        private long duration;
        private RequestListener<T> listener;

        public CacheFillerListener(APICache cache, String cacheKey, long duration, RequestListener<T> listener) {
            this.cache = cache;
            this.cacheKey = cacheKey;
            this.duration = duration;
            this.listener = listener;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if(listener != null)
                listener.onRequestFailure(spiceException);
        }

        @Override
        public void onRequestSuccess(T t) {
            if(t.isSuccess()) {
                cache.put(APICache.CacheType.MEMORY, cacheKey, t, duration);
            }
            if(listener != null)
                listener.onRequestSuccess(t);
        }
    }
}
