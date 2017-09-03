package com.mobibrw.persist.biz;

import android.support.annotation.NonNull;

import com.mobibrw.lego.ILego;
import com.mobibrw.lego.SimpleLegoBizBundle;
import com.mobibrw.persist.api.IPersistApi;
import com.mobibrw.persist.api.IPersistListener;
import com.mobibrw.persist.api.TimeInfo;
import com.mobibrw.utils.ListenerManager;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

import java.util.List;

/**
 * Created by longsky on 2017/8/14.
 */

class PersistBizBu extends SimpleLegoBizBundle<IPersistListener> implements IPersistApi {

    @Override
    protected void onBundleCreate(@NonNull final ILego lego) {
        LitePal.initialize(lego.getLegoContext());
        LitePalDB litePalDB = new LitePalDB(this.defaultDatabaseName, this.defaultDatabaseVer);
        litePalDB.addClassName(TimeItem.class.getName());
        LitePal.use(litePalDB);
    }

    @Override
    protected void onBundleDestroy() {

    }

    @Override
    public String persistTimeItem(final String content,final String gmt) {
        TimeItem item = new TimeItem();
        item.setContent(content);
        item.setGmt(gmt);
        item.save();
        broadcastPersistBizChanged();
        return "" + item.getRecordId();
    }

    private void postPersistBizChangedOnMainThread(final IPersistListener l) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                l.onPersistMessageChanged();
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
    public void removeTimeItem(final String id) {

    }

    @Override
    public void coverTimeItem(final String id, final String content) {

    }

    @Override
    public long getTimeItemsCount() {
        return DataSupportEx.count(TimeItem.class);
    }

    @Override
    public TimeInfo getTimeInfoByOffset(int offset) {
        List<TimeItem> items = DataSupportEx.order("id desc").offset(offset).limit(1).find(TimeItem.class);
        if((null != items)&& (items.size() > 0)){
            TimeItem item = items.get(0);
            TimeInfo info = new TimeInfo();
            info.setId(item.getRecordId());
            info.setContent(item.getContent());
            return info;
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

    private final static String defaultDatabaseName = "PersistTimeBiz";
    private final static int defaultDatabaseVer = 1;
}
