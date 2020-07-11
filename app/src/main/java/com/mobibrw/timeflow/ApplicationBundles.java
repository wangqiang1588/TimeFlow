package com.mobibrw.timeflow;

import android.support.annotation.NonNull;

import com.mobibrw.lego.LegoBundleNames;

import java.util.ArrayList;

/**
 * Created by longsky on 2017/9/3.
 */

public class ApplicationBundles {

    @NonNull
    public static ArrayList<String> getApplicationBundles() {
        final ArrayList<String> bundles = new ArrayList<>();
        bundles.add(LegoBundleNames.PersistBizBu);
        bundles.add(LegoBundleNames.PersistApiBu);
        return bundles;
    }
}
