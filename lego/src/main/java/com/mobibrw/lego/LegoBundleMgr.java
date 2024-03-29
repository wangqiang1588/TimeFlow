package com.mobibrw.lego;

import androidx.annotation.NonNull;

import com.mobibrw.utils.LogEx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;

/**
 * Created by longsky on 2017/8/20.
 */

public final class LegoBundleMgr implements ILegoMgr {

    private final ILego lego;
    private final LinkedHashMap<String, LegoBundle> mBundles = new LinkedHashMap<>();
    private final ArrayList<String> mBundleNames = new ArrayList<>();

    public LegoBundleMgr(@NonNull final ILego lego, @NonNull final ArrayList<String> bundles) {
        this.lego = lego;
        mBundleNames.clear();
        mBundleNames.addAll(bundles);
    }

    private String tag() {
        return this.getClass().getSimpleName();
    }

    @Override
    public LegoBundle getBundle(@NonNull final String name) {
        return mBundles.get(name);
    }

    public void onLegoApplicationCreate() {
        for (String name : mBundleNames) {
            final boolean bSuccess = loadOneBundle(name);
            if (!bSuccess) {
                LogEx.e(tag(), "load bundle \"" + name + "\" failed!");
            }
        }
    }

    public void onLegoApplicationTerminate() {
        final ListIterator<LinkedHashMap.Entry<String, LegoBundle>> bundles = new ArrayList<>(mBundles.entrySet()).listIterator(mBundles.size());
        while (bundles.hasPrevious()) {
            final LinkedHashMap.Entry<String, LegoBundle> element = bundles.previous();
            final String bundleName = element.getKey();
            final LegoBundle legoBundle = element.getValue();
            legoBundle.onBundleDestroy();
            mBundles.remove(bundleName);
        }
        mBundles.clear();
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    protected boolean loadOneBundle(@NonNull final String name) {
        boolean bSuccess = false;

        try {
            do {
                if (mBundles.containsKey(name)) {
                    LogEx.e(tag(), "duplicated bundle: " + name);
                    break;
                }

                final Class<? extends LegoBundle> cls = this.lego.getLegoContext().getClassLoader().loadClass(name).asSubclass(LegoBundle.class);

                final Constructor<? extends LegoBundle> constructor = cls.getDeclaredConstructor();
                constructor.setAccessible(true);

                final LegoBundle bundle = constructor.newInstance();

                mBundles.put(name, bundle);

                bundle.onBundleCreate(this.lego);

                bSuccess = true;
            } while (false);
        } catch (ClassNotFoundException e) {
            LogEx.e(tag(), e.toString() + ":" + name);
        } catch (NoSuchMethodException e) {
            LogEx.e(tag(), e.toString() + ":" + name);
        } catch (InstantiationException e) {
            LogEx.e(tag(), e.toString() + ":" + name);
        } catch (IllegalAccessException e) {
            LogEx.e(tag(), e.toString() + ":" + name);
        } catch (InvocationTargetException e) {
            LogEx.e(tag(), e.toString() + ":" + name);
        } catch (ClassCastException e) {
            LogEx.e(tag(), e.toString() + ":" + name);
        }

        if (bSuccess) {
            LogEx.d(tag(), "load success: " + name);
        } else {
            LogEx.e(tag(), "load failed: " + name);
        }
        return bSuccess;
    }
}
