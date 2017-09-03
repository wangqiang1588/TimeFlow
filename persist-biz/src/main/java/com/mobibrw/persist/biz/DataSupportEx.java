package com.mobibrw.persist.biz;

import org.litepal.crud.DataSupport;

/**
 * Created by longsky on 2017/9/3.
 */

public class DataSupportEx extends DataSupport {

    public long getRecordId(){
        return getBaseObjId();
    }
}
