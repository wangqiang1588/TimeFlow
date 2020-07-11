package com.mobibrw.persist.api;

/**
 * Created by longsky on 2017/9/3.
 */

public class TimeFlowCase {

    private String key;
    private String content;
    private String gmt;
    private String modified; // time when case edited

    public String getKey() {
        return key;
    }

    public void setKey(final String k) {
        this.key = k;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String c) {
        this.content = c;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(final String t) {
        this.gmt = t;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(final String m) {
        this.modified = m;
    }
}
