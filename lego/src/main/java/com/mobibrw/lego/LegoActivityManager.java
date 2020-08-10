package com.mobibrw.lego;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mobibrw.utils.EquatableWeakReference;

import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;

class LegoActivityManager implements Application.ActivityLifecycleCallbacks {
    private final Application application;
    private final LinkedHashSet<EquatableWeakReference<Activity>> activities = new LinkedHashSet<>();

    private WeakReference<Activity> currentTopActivity;

    @MainThread
    public LegoActivityManager(final @NonNull Context context) {
        application = ((Application) context.getApplicationContext());
        application.registerActivityLifecycleCallbacks(this);
    }

    @MainThread
    public void onLegoApplicationTerminate() {
        application.unregisterActivityLifecycleCallbacks(this);
        activities.clear();
    }

    @Nullable
    public Activity getCurrentForegroundActivity() {
        return this.currentTopActivity == null ? null : this.currentTopActivity.get();
    }

    /**
     * Get Any Activity for API use . for example : check permission from service
     *
     * @return Any Alive Activity
     */
    @Nullable
    public Activity getAnyActivity() {
        for (EquatableWeakReference<Activity> av : activities) {
            final Activity activity = av.get();
            if (null != activity) {
                return activity;
            }
        }
        return null;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        activities.add(new EquatableWeakReference<>(activity));
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentTopActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        currentTopActivity = null;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activities.remove(new EquatableWeakReference<>(activity));
    }
}
