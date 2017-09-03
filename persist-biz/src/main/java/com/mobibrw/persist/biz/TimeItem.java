package com.mobibrw.persist.biz;

import org.litepal.annotation.Column;

/**
 * Created by longsky on 2017/9/2.
 */

public class TimeItem extends DataSupportEx {
    @Column(nullable = false, defaultValue = "")
    private String content;
    @Column(nullable = false)
    private String gmt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }
}
