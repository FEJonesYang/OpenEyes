package log;

import android.util.Log;

public class LogUtils {

    public static void logError(String tag, int errorCode, String errorMsg) {
        Log.e(tag, "Current thread is " +
                Thread.currentThread().getName() + " thread. Messages are as follows:\n" +
                "errorCode is " + errorCode + ". errorMessage is " + errorMsg);
    }

    public static void logSuccess(String tag, String msg) {
        Log.d(tag, "Current thread is " +
                Thread.currentThread().getName() + " thread. Messages are as follows:\n" + msg);
    }
}
