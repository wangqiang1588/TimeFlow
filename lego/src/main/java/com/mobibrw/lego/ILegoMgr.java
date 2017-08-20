package com.mobibrw.lego;

import android.support.annotation.NonNull;

/**
 * Created by longsky on 2017/8/20.
 */

public interface ILegoMgr {
    LegoBundle getBundle(@NonNull final String name);
}
