package com.mobibrw.utils;

import android.content.Context;
import android.support.annotation.StringRes;

public class StringUtils {
    public static String getString(final Context context, final @StringRes int id) {
        return context.getResources().getString(id);
    }
}
