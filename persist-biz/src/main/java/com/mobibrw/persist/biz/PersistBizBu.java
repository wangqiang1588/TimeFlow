package com.mobibrw.persist.biz;

import android.content.ContentValues;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.mobibrw.lego.ILego;
import com.mobibrw.lego.SimpleLegoBizBundle;
import com.mobibrw.persist.api.IPersistApi;
import com.mobibrw.persist.api.IPersistListener;
import com.mobibrw.persist.api.TimeFlowCase;
import com.mobibrw.utils.ListenerManager;

import org.litepal.FluentQuery;
import org.litepal.LitePal;
import org.litepal.LitePalDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longsky on 2017/8/14.
 */

class PersistBizBu extends SimpleLegoBizBundle<IPersistListener> implements IPersistApi {

    private TimeFlowCase transFromTimeFlowPersistCase(TimeFlowPersistCase persistCase) {
        final TimeFlowCase tfCase = new TimeFlowCase();
        tfCase.setKey(persistCase.getKey());
        tfCase.setContent(persistCase.getContent());
        tfCase.setGmt(persistCase.getGmt());
        tfCase.setModified(persistCase.getModified());
        return tfCase;
    }

    @MainThread
    @Override
    protected void onBundleCreate(@NonNull final ILego lego) {
        LitePal.initialize(lego.getLegoContext());
        final LitePalDB litePalDB = new LitePalDB(this.defaultDatabaseName, this.defaultDatabaseVer);
        litePalDB.addClassName(TimeFlowPersistCase.class.getName());
        LitePal.use(litePalDB);
    }

    @MainThread
    @Override
    protected void onBundleDestroy() {
        super.onBundleDestroy();
    }

    @Override
    public boolean persistTimeFlowCase(final String key, final String content, final String modifyTime, final String gmt) {
        final TimeFlowPersistCase tfCase = new TimeFlowPersistCase();
        tfCase.setKey(key);
        tfCase.setContent(content);
        tfCase.setGmt(gmt);
        tfCase.setState(TimeFlowPersistCase.TIME_FLOW_PERSIST_CASE_STATE_ACTIVE);
        tfCase.setModified(modifyTime);
        final boolean success = tfCase.save();
        broadcastPersistBizChanged();
        return success;
    }

    private void postPersistBizChangedOnMainThread(final IPersistListener l) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                l.onPersistBizChanged();
            }
        };
        PersistBizBu.this.postLegoRunnable(l, runnable);
    }

    private void broadcastPersistBizChanged() {
        super.forEachLegoListener(new ListenerManager.IForEachListener<IPersistListener>() {
            @Override
            public void onForEachListener(@NonNull final IPersistListener l) {
                postPersistBizChangedOnMainThread(l);
            }
        });
    }

    @Override
    public void removeTimeFlowCase(final String key) {
        ContentValues values = new ContentValues();
        values.put("state", TimeFlowPersistCase.TIME_FLOW_PERSIST_CASE_STATE_DEL);
        LitePal.updateAll(TimeFlowPersistCase.class, values, "key=?", key);
        broadcastPersistBizChanged();
    }

    @Override
    public long loadTimeFlowCaseLength() {
        return LitePal.count(TimeFlowPersistCase.class);
    }

    @Override
    public boolean isTimeFlowCaseKeyExists(final String key) {
        final List<TimeFlowPersistCase> persistCases = LitePal.where("key=?", key).limit(1).find(TimeFlowPersistCase.class);
        return persistCases.size() > 0;
    }

    @Override
    public ArrayList<TimeFlowCase> loadCompleteTimeFlowCases(final int limit) {
        final ArrayList<TimeFlowCase> timeFlowCases = new ArrayList<>();
        final FluentQuery fluentQuery = LitePal.order("id desc");
        fluentQuery.max(TimeFlowPersistCase.class,"id", int.class);
        fluentQuery.where("state=? group by key", TimeFlowPersistCase.TIME_FLOW_PERSIST_CASE_STATE_ACTIVE);
        if(TIME_FLOW_LOAD_LIMIT_NONE != limit) {
            fluentQuery.limit(limit);
        }
        final List<TimeFlowPersistCase> persistResCases = fluentQuery.find(TimeFlowPersistCase.class);
        if((null != persistResCases)&& (persistResCases.size() > 0)) {
            for(TimeFlowPersistCase persistCase : persistResCases) {
                final TimeFlowCase tfCase = transFromTimeFlowPersistCase(persistCase);
                timeFlowCases.add(tfCase);
            }
        }
        return timeFlowCases;
    }

    @Override
    public TimeFlowCase loadTimeFlowCase(final String key) {
        final List<TimeFlowPersistCase> persistCases = LitePal.order("id desc").where("key=?", key).limit(1).find(TimeFlowPersistCase.class);
        if((null != persistCases)&& (persistCases.size() > 0)){
            final TimeFlowPersistCase persistCase = persistCases.get(0);
            final TimeFlowCase tfCase = transFromTimeFlowPersistCase(persistCase);
            return tfCase;
        }
        return null;
    }

    @Override
    public boolean registerPersistListener(IPersistListener l) {
        return this.registerListener(l);
    }

    @Override
    public void unRegisterPersistListener(IPersistListener l) {
        this.unRegisterListener(l);
    }

    private final static String defaultDatabaseName = "TimeFlowBiz";
    private final static int defaultDatabaseVer = 1;
}
