package com.mobibrw.utils;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by longsky on 2017/9/3.
 */

public class ListenerManager<T> {

    private final LinkedHashSet<EquatableWeakReference<T>> listeners = new LinkedHashSet<>();
    private final Object lock = new Object();
    private final LinkedHashMap<RunnableEx, WeakReference<T>> runnableExs = new LinkedHashMap<>();
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    public boolean registerListener(@NonNull final T listener) {
        final EquatableWeakReference<T> equatableWeakListener = new EquatableWeakReference<>(listener);
        synchronized (lock) {
            if (listeners.contains(equatableWeakListener)) {
                return false;
            }
            listeners.add(equatableWeakListener);
            cancelPostedRunnable(listener);
            cleanupDeadListener();
        }
        return true;
    }

    public boolean unRegisterListener(@NonNull final T listener) {
        final EquatableWeakReference<T> equatableWeakListener = new EquatableWeakReference<>(listener);
        synchronized (lock) {
            if (!listeners.contains(equatableWeakListener)) {
                return false;
            }
            listeners.remove(equatableWeakListener);
            cancelPostedRunnable(listener);
            cleanupDeadListener();
        }
        return true;
    }

    private void cleanupDeadListener() {
        final ArrayList<EquatableWeakReference<T>> cleanupArr = new ArrayList<>();
        final Iterator<EquatableWeakReference<T>> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            final EquatableWeakReference<T> weak = iterator.next();
            if (null != weak) {
                final T strong = weak.get();
                if (null == strong) {
                    cleanupArr.add(weak);
                }
            }
        }
        // cleanup dead listener
        for (EquatableWeakReference<T> weak : cleanupArr) {
            listeners.remove(weak);
        }
    }

    private void cancelPostedRunnable(@NonNull final T listener) {
        final ArrayList<RunnableEx> runnableArr = new ArrayList<>();
        final Iterator<LinkedHashMap.Entry<RunnableEx, WeakReference<T>>> iterator = runnableExs.entrySet().iterator();
        while (iterator.hasNext()) {
            final LinkedHashMap.Entry<RunnableEx, WeakReference<T>> element = iterator.next();
            final RunnableEx rx = element.getKey();
            if (null != rx) {
                final WeakReference<T> weak = element.getValue();
                if (null != weak) {
                    final T strong = weak.get();
                    if (null != strong) {
                        if (strong.equals(listener)) {
                            mHandler.removeCallbacks(rx);
                            runnableArr.add(rx);
                        }
                    } else {
                        mHandler.removeCallbacks(rx);
                        runnableArr.add(rx);
                    }
                } else {
                    mHandler.removeCallbacks(rx);
                    runnableArr.add(rx);
                }
            }
        }
        for (Runnable r : runnableArr) {
            runnableExs.remove(r);
        }
    }

    public void clearListener() {
        synchronized (lock) {
            final Iterator<EquatableWeakReference<T>> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                final EquatableWeakReference<T> listener = iterator.next();
                if (null != listener) {
                    final T strongListener = listener.get();
                    if (null != strongListener) {
                        cancelPostedRunnable(strongListener);
                    }
                }
            }
            listeners.clear();
            runnableExs.clear();
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void forEachListener(@NonNull final IForEachListener<T> listener) {
        final ArrayList<EquatableWeakReference<T>> cleanupArr = new ArrayList<>();
        synchronized (lock) {
            final Iterator<EquatableWeakReference<T>> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                final EquatableWeakReference<T> weak = iterator.next();
                if (null != weak) {
                    final T strong = weak.get();
                    if (null != strong) {
                        if (null != listener) {
                            listener.onForEachListener(strong);
                        } else {
                            cleanupArr.add(weak);
                        }
                    } else {
                        cleanupArr.add(weak);
                    }
                }
            }
            // cleanup dead listener
            for (EquatableWeakReference<T> weak : cleanupArr) {
                listeners.remove(weak);
            }
        }
    }

    public boolean postRunnable(@NonNull final T listener, @NonNull final Runnable runnable) {
        final RunnableEx<T> runnableEx = new RunnableEx<T>(runnableExs, lock, runnable);
        synchronized (lock) {
            runnableExs.put(runnableEx, new WeakReference<T>(listener));
            if (!mHandler.post(runnableEx)) {
                runnableExs.remove(runnableEx);
                return false;
            }
        }
        return true;
    }

    public interface IForEachListener<T> {
        void onForEachListener(@NonNull final T listener);
    }

    private static final class RunnableEx<T> implements Runnable {

        private final Object lock;
        private Runnable runnable;
        private LinkedHashMap<RunnableEx, WeakReference<T>> container;

        public RunnableEx(@NonNull final LinkedHashMap<RunnableEx, WeakReference<T>> container, @NonNull final Object criticalSection, @NonNull final Runnable runnable) {
            this.runnable = runnable;
            this.container = container;
            lock = criticalSection;
        }

        public void run() {
            synchronized (lock) {
                container.remove(this);
            }
            runnable.run();
        }
    }
}
