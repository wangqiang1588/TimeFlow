package com.mobibrw.lego;

import android.support.annotation.NonNull;

/**
 * Created by longsky on 17-7-6.
 */

public abstract class LegoBundle {
    abstract protected void onBundleCreate(@NonNull final ILego lego);
    abstract protected void onBundleDestroy();
}
