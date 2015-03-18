package it.enricocandino.sample.service.cache;

import android.content.Context;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;


/**
 * Created by enrico on 03/03/15.
 */
public class APICache {

    private static final String LOG_TAG = "APICache";

    public enum CacheType { MEMORY, DISK }

    private static APICache INSTANCE;
    private LruCache cache;

    private APICache(Context context, int memorySize, long diskSize) {
        try {
            cache = new LruCache<String, CachedObject>(memorySize);
            SimpleDiskCache.open(context.getCacheDir(), 1, diskSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static APICache getInstance() {
        return INSTANCE;
    }

    public static void init(Context context, int memorySize, int diskSize) {
        if(INSTANCE == null)
            INSTANCE = new APICache(context, memorySize, diskSize);
        else
            throw new RuntimeException("APICache already initialized");
    }

    public <V> V get(CacheType cacheType, String cacheKey) {
        V value = null;
        switch (cacheType) {
            case MEMORY:
                value = getFromMemory(cacheKey);
                break;
            case DISK:
                value = getFromMemory(cacheKey);
                break;
        }
        return value;
    }

    public <V> V put(CacheType cacheType, String cacheKey, V value, long cacheDuration) {
        V previous = null;
        switch (cacheType) {
            case MEMORY:
                previous = putInMemory(cacheKey, value, cacheDuration);
                break;
            case DISK:
                previous = putInMemory(cacheKey, value, cacheDuration);
                break;
        }
        return previous;
    }

    public <V> V getFromMemory(String cacheKey) {
        Log.d(LOG_TAG, "Getting object from cache with key "+cacheKey);

        V value = null;
        CachedObject<V> cached = (CachedObject<V>) cache.get(cacheKey);
        if(cached != null) {
            Log.d(LOG_TAG, "Object found! [HIT]");
            if(System.currentTimeMillis() < cached.duration)
                value = cached.value;
            else
                Log.d(LOG_TAG, "Object in cache is expired!");
        } else {
            Log.d(LOG_TAG, "Object not found! [MISS]");
        }
        return value;
    }

    public <V> V putInMemory(String cacheKey, V value, long cacheDuration) {
        Log.d(LOG_TAG, "Putting object in cache with key "+cacheKey+ " and duration "+cacheDuration);

        V previous = null;

        CachedObject<V> cachedObject = new CachedObject<>(value, cacheDuration);
        CachedObject<V> previousCachedObject = (CachedObject<V>) cache.put(cacheKey, cachedObject);
        if(previousCachedObject != null)
            previous = previousCachedObject.value;

        return previous;
    }

    class CachedObject<V> {
        V value;
        long duration;

        CachedObject(V value, long duration) {
            this.value = value;
            this.duration = System.currentTimeMillis() + duration;
        }
    }
}
