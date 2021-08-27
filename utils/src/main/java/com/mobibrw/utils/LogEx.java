package com.mobibrw.utils;

import androidx.annotation.NonNull;
import android.util.Log;

/**
 * Created by longsky on 2017/8/20.
 */

public final class LogEx {
    public static void i(@NonNull final String tag, @NonNull final String msg) {
        Log.i(tag, msg);
    }

    public static void e(@NonNull final String tag, @NonNull final String msg) {
        Log.e(tag, msg);
    }

    public static void d(@NonNull final String tag, @NonNull final String msg) {
        Log.d(tag, msg);
    }

    public static void v(@NonNull final String tag, @NonNull final String msg) {
        Log.v(tag, msg);
    }

    public static void v(@NonNull final String tag, @NonNull final String msg, @NonNull final Throwable e) {
        Log.v(tag, msg, e);
    }

    public static void w(@NonNull final String tag, @NonNull final String msg) {
        Log.w(tag, msg);
    }
}
