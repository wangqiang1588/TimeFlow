package com.mobibrw.timeflow;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;

import com.mobibrw.lego.ILegoAppContext;
import com.mobibrw.lego.LegoApplication;

import java.util.ArrayList;

/**
 * Created by longsky on 17-7-6.
 */

public class TimeFlowApplication extends Application implements ILegoAppContext {

    private final LegoApplication legoApplication = new LegoApplication(this);

    @Override
    public void onCreate() {
        super.onCreate();
        legoApplication.onApplicationCreate();
    }

    @Override
    public void onTerminate() {
        legoApplication.onApplicationTerminate();
        super.onTerminate();
    }

    @NonNull
    @Override
    public Context getAppContext() {
        return this.getApplicationContext();
    }

    @NonNull
    @Override
    public ArrayList<String> getAppBundles() {
        return ApplicationBundles.getApplicationBundles();
    }
}
