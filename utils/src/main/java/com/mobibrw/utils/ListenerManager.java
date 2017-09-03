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

    public interface IForEachListener<T> {
        void onForEachListener(@NonNull final T listener);
    }

    public boolean registerListener(@NonNull final T listener) {
        final EquatableWeakReference<T> equatableWeakListener = new EquatableWeakReference<>(listener);
        synchronized (listenersLock) {
            if (listeners.contains(equatableWeakListener)) {
                return false;
            }
            listeners.add(equatableWeakListener);
            cancelPostedRunnable(listener);
        }
        return true;
    }

    public boolean unRegisterListener(@NonNull final T listener) {
        final EquatableWeakReference<T> equatableWeakListener = new EquatableWeakReference<>(listener);
        synchronized (listenersLock) {
            if (!listeners.contains(equatableWeakListener)) {
                return false;
            }
            listeners.remove(equatableWeakListener);
        }
        cancelPostedRunnable(listener);
        return true;
    }

    private void cancelPostedRunnable(@NonNull final T listener) {
        synchronized (runnableExsLock) {
            final ArrayList<RunnableEx> runnables = new ArrayList<>();
            final Iterator<LinkedHashMap.Entry<RunnableEx, WeakReference<T>>> iterator = runnableExs.entrySet().iterator();
            while (iterator.hasNext()) {
                final LinkedHashMap.Entry<RunnableEx, WeakReference<T>> element = iterator.next();
                final RunnableEx runnable = element.getKey();
                if (null != runnable) {
                    final WeakReference<T> weak = element.getValue();
                    if (null != weak) {
                        final T strong = weak.get();
                        if (null != strong) {
                            if (strong.equals(listener)) {
                                mHandler.removeCallbacks(runnable);
                                runnables.add(runnable);
                            }
                        } else {
                            mHandler.removeCallbacks(runnable);
                            runnables.add(runnable);
                        }
                    } else {
                        mHandler.removeCallbacks(runnable);
                        runnables.add(runnable);
                    }
                } else {
                    runnables.add(runnable);
                }
            }
            for (Runnable runnable : runnables) {
                runnableExs.remove(runnable);
            }
        }
    }

    public void clearListener() {
        synchronized (listenersLock) {
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
        }
    }

    public void forEachListener(@NonNull final IForEachListener<T> listener) {
        synchronized (listenersLock) {
            final Iterator<EquatableWeakReference<T>> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                final EquatableWeakReference<T> weak = iterator.next();
                if (null != weak) {
                    final T strong = weak.get();
                    if (null != strong) {
                        if (null != listener) {
                            listener.onForEachListener(strong);
                        }
                    }
                }
            }
        }
    }

    public boolean postRunnable(@NonNull final T listener, @NonNull final Runnable runnable) {
        final RunnableEx<T> runnableEx = new RunnableEx<T>(runnableExs, runnableExsLock, runnable);
        synchronized (runnableExsLock) {
            runnableExs.put(runnableEx, new WeakReference<T>(listener));
        }
        if (!mHandler.post(runnableEx)) {
            synchronized (runnableExsLock) {
                runnableExs.remove(runnableEx);
            }
            return false;
        }
        return true;
    }

    private static final class RunnableEx<T> implements Runnable {

        private Runnable runnable;
        private LinkedHashMap<RunnableEx, WeakReference<T>> container;
        private final Object lock;

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

    private final LinkedHashSet<EquatableWeakReference<T>> listeners = new LinkedHashSet<>();
    private final Object listenersLock = new Object();
    private final LinkedHashMap<RunnableEx, WeakReference<T>> runnableExs = new LinkedHashMap<>();
    private final Object runnableExsLock = new Object();
    private static final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
}
