package com.mobibrw.lego;

import android.support.annotation.NonNull;

import com.mobibrw.utils.LogEx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

/**
 * Created by longsky on 2017/8/20.
 */

public final class LegoBundleMgr implements ILegoMgr {

    private final String tag() {
        return this.getClass().getSimpleName();
    }

    public LegoBundleMgr(@NonNull final ILego lego){
        this.lego = lego;
    }

    @Override
    public LegoBundle getBundle(@NonNull final String name) {
        final LegoBundle legoBundle = mBundles.get(name);
        return legoBundle;
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

                final Constructor<? extends LegoBundle> constructor = cls.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);

                final LegoBundle bundle = constructor.newInstance(name);

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

    private ILego lego;
    private final LinkedHashMap<String, LegoBundle> mBundles = new LinkedHashMap<>();
}
