package com.mobibrw.persist.api;

/**
 * Created by longsky on 2017/8/20.
 */

public interface IPersistApi {
    void persistTimeItem(String content);
    void removeTimeItem(String id);
    void coverTimeItem(String id,String content);
}
