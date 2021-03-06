package com.mobibrw.lego;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.mobibrw.utils.ListenerManager;

/**
 * Created by longsky on 2017/9/3.
 */

abstract public class SimpleLegoBizBundle<T> extends LegoBizBundle {

    private final ListenerManager<T> listenersManager = new ListenerManager<>();
    private volatile boolean isDestroyed = false;

    protected boolean isDestroyed() {
        return isDestroyed;
    }

    protected boolean registerListener(@NonNull final T listener) {
        return listenersManager.registerListener(listener);
    }

    protected boolean unRegisterListener(@NonNull final T listener) {
        return listenersManager.unRegisterListener(listener);
    }

    protected void forEachLegoListener(@NonNull final ListenerManager.IForEachListener<T> listener) {
        listenersManager.forEachListener(listener);
    }

    protected boolean postLegoRunnable(@NonNull final T listener, @NonNull final Runnable runnable) {
        return listenersManager.postRunnable(listener, runnable);
    }

    @Override
    @CallSuper
    protected void onBundleDestroy() {
        isDestroyed = true;
        listenersManager.clearListener();
    }

}
