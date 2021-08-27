package com.mobibrw.utils;

import android.content.Context;
import androidx.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {

    public static void shortToast(Context context, @StringRes int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }

}
