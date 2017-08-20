package com.mobibrw.lego;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by longsky on 17-7-6.
 */

public class LegoApplication implements ILego {

    public LegoApplication(Context ctx){
       this.ctx = ctx;
    }

    public void onApplicationCreate(){
        mLegoBundleMgr = new LegoBundleMgr(this);
    }

    public void onApplicationTerminate(){

    }

    public ILegoMgr getLegoBundleMgr() {
        return mLegoBundleMgr;
    }

    @NonNull
    @Override
    public Context getLegoContext() {
        return this.ctx;
    }

    private Context ctx;
    private LegoBundleMgr mLegoBundleMgr;
}
