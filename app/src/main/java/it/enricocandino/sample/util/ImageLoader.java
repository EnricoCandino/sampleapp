package it.enricocandino.sample.util;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by enrico on 21/02/15.
 */
public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Picasso picasso = Pic.INSTANCE.getPicasso(context);
        picasso.load(url).into(imageView);
    }

    private enum Pic {

        INSTANCE;

        private Picasso picasso;

        public Picasso getPicasso(Context context) {
            if (picasso == null) {
                Picasso.Builder builder = new Picasso.Builder(context);
                builder.downloader(new CustomUrlConnectionDownloader(context));
                picasso = builder.build();
            }
            return picasso;
        }
    }

    /*
     * UrlConnectionDownloader used for the Basic Authentication
     */
    private static class CustomUrlConnectionDownloader extends UrlConnectionDownloader {

        public CustomUrlConnectionDownloader(Context context) {
            super(context);
        }

        @Override
        protected HttpURLConnection openConnection(Uri path) throws IOException {
            HttpURLConnection c = (HttpURLConnection) new URL(path.toString()).openConnection();
            String string = "Basic " + Base64.encodeToString("username:password".getBytes(), Base64.NO_WRAP);
            c.setRequestProperty("Authorization", string);
            return c;
        }
    }

}
