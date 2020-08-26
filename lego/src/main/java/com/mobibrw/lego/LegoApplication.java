package com.mobibrw.lego;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by longsky on 17-7-6.
 */

public class LegoApplication implements ILego {

    private final ILegoAppContext legoAppContext;
    private LegoActivityMgr legoActivityMgr;
    private LegoBundleMgr legoBundleMgr;

    public LegoApplication(@NonNull final ILegoAppContext legoAppContext) {
        this.legoAppContext = legoAppContext;
    }

    public void onApplicationCreate() {
        legoActivityMgr = new LegoActivityMgr(legoAppContext.getAppContext());
        legoBundleMgr = new LegoBundleMgr(this, this.legoAppContext.getAppBundles());
        legoBundleMgr.onLegoApplicationCreate();
    }

    public void onApplicationTerminate() {
        legoActivityMgr.onLegoApplicationTerminate();
        legoBundleMgr.onLegoApplicationTerminate();
    }

    public ILegoMgr getLegoBundleMgr() {
        return legoBundleMgr;
    }

    @NonNull
    @Override
    public Context getLegoContext() {
        return this.legoAppContext.getAppContext();
    }

    @Nullable
    @Override
    public Activity getAnyActivity() {
        return legoActivityMgr.getAnyActivity();
    }

    @Nullable
    @Override
    public Activity getCurrentForegroundActivity() {
        return legoActivityMgr.getCurrentForegroundActivity();
    }
}
