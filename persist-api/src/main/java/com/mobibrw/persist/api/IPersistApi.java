package com.mobibrw.persist.api;

import java.util.ArrayList;

/**
 * Created by longsky on 2017/8/20.
 */

public interface IPersistApi {
    int TIME_FLOW_LOAD_LIMIT_NONE = -1;

    boolean persistTimeFlowCase(final String key, final String content, final String modifyTime, final String gmt);

    boolean isTimeFlowCaseKeyExists(final String key);

    ArrayList<TimeFlowCase> loadCompleteTimeFlowCases(final int limit);

    void removeTimeFlowCase(final String key);

    long loadTimeFlowCaseLength();

    TimeFlowCase loadTimeFlowCase(final String key);

    boolean registerPersistListener(final IPersistListener l);

    void unRegisterPersistListener(final IPersistListener l);
}
