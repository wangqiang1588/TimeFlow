package com.mobibrw.lego;

import android.support.annotation.NonNull;

/**
 * Created by longsky on 17-7-6.
 */

public abstract class LegoApiBundle extends LegoBundle {

    protected LegoBundle getLegoBundle(@NonNull final ILego lego, @NonNull final String name) {
        if (lego instanceof LegoApplication) {
            LegoApplication legoApplication = (LegoApplication) lego;
            ILegoMgr legoBundleMgr = legoApplication.getLegoBundleMgr();
            return legoBundleMgr.getBundle(name);
        }
        return null;
    }
}
