package com.mobibrw.persist.api;

/**
 * Created by longsky on 2017/8/20.
 */

public interface IPersistApi {
    String persistTimeItem(final String content,final String gmt);
    void removeTimeItem(final String id);
    void coverTimeItem(final String id,final String content);
    long getTimeItemsCount();

    TimeInfo getTimeInfoByOffset(int offset);

    boolean registerPersistListener(final IPersistListener l);
    void unRegisterPersistListener(final IPersistListener l);
}
