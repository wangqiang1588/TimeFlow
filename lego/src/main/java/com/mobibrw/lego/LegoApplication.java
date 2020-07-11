package com.mobibrw.lego;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by longsky on 17-7-6.
 */

public class LegoApplication implements ILego {

    private final ILegoAppContext legoAppContext;
    private LegoBundleMgr legoBundleMgr;

    public LegoApplication(@NonNull final ILegoAppContext legoAppContext) {
        this.legoAppContext = legoAppContext;
    }

    public void onApplicationCreate() {
        legoBundleMgr = new LegoBundleMgr(this, this.legoAppContext.getAppBundles());
        legoBundleMgr.onLegoApplicationCreate();
    }

    public void onApplicationTerminate() {
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
}
