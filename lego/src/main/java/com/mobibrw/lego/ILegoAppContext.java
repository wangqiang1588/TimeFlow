package com.mobibrw.lego;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by longsky on 2017/9/3.
 */

public interface ILegoAppContext {
    @NonNull
    Context getAppContext();

    @NonNull
    ArrayList<String> getAppBundles();
}
