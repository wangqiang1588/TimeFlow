package com.mobibrw.persist.api;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.mobibrw.lego.ILego;
import com.mobibrw.lego.LegoApiBundle;
import com.mobibrw.lego.LegoBundleNames;

/**
 * Created by longsky on 2017/8/14.
 */

public final class PersistApiBu extends LegoApiBundle {

    @MainThread
    @Override
    protected void onBundleCreate(@NonNull final ILego lego) {
        mApi = (IPersistApi) getLegoBundle(lego,LegoBundleNames.PersistBizBu);
    }

    @MainThread
    @Override
    protected void onBundleDestroy() {
        mApi = null;
    }

    // /////////////////////////////////////////////////////////////////////////////

    static public IPersistApi api() {
        return mApi;
    }

    static private IPersistApi mApi;
}
