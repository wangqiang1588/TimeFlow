package com.mobibrw.timeflow;

import android.app.Application;

import com.mobibrw.lego.LegoApplication;

/**
 * Created by longsky on 17-7-6.
 */

public class TimeFlowApplication extends Application {

    private final LegoApplication mLegoApplication = new LegoApplication(this);

    @Override
    public void onCreate(){
        super.onCreate();
        mLegoApplication.onApplicationCreate();
    }

    @Override
    public void onTerminate(){
        mLegoApplication.onApplicationTerminate();
        super.onTerminate();
    }
}
