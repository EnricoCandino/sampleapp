package it.enricocandino.sample.util;

/**
 * Created by enrico on 21/02/15.
 */
public class Log {

    public static int LOG_LEVEL = 5;

    public static boolean VERBOSE   = LOG_LEVEL > 4;
    public static boolean DEBUG     = LOG_LEVEL > 3;
    public static boolean INFO      = LOG_LEVEL > 2;
    public static boolean WARN      = LOG_LEVEL > 1;
    public static boolean ERROR     = LOG_LEVEL > 0;

    public static void v(String tag, String message) {
        if (VERBOSE)
            android.util.Log.v(tag, message);
    }

    public static void d(String tag, String message) {
        if (DEBUG)
            android.util.Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        if (INFO)
            android.util.Log.i(tag, message);
    }

    public static void w(String tag, String message) {
        if (WARN)
            android.util.Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        if (ERROR)
            android.util.Log.e(tag, message);
    }

    public static void e(String tag, String message, Exception e) {
        if (ERROR)
            android.util.Log.e(tag, message, e);
    }

}
