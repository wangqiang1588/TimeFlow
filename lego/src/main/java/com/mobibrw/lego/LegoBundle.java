package com.mobibrw.lego;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * Created by longsky on 17-7-6.
 */

public abstract class LegoBundle {
    @MainThread
    abstract protected void onBundleCreate(@NonNull final ILego lego);

    @MainThread
    abstract protected void onBundleDestroy();
}
