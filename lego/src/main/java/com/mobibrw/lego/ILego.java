package com.mobibrw.lego;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by longsky on 2017/8/20.
 */

public interface ILego {

    @NonNull
    Context getLegoContext();

    /**
     * Get Any Activity for API use . for example : check permission from service
     *
     * @return Any Alive Activity
     */
    @Nullable
    Activity getAnyActivity();

    @Nullable
    Activity getCurrentForegroundActivity();
}
