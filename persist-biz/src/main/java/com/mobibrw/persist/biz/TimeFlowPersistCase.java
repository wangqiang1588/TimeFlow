package com.mobibrw.persist.biz;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * Created by longsky on 2017/9/2.
 */
public class TimeFlowPersistCase extends LitePalSupport {

    public static String TIME_FLOW_PERSIST_CASE_STATE_DEL = "deleted";

    public static String TIME_FLOW_PERSIST_CASE_STATE_ACTIVE = "active";

    @Column(nullable = false)
    private String key; // ID of Case

    @Column(nullable = false, defaultValue = "")
    private String content;

    @Column(nullable = false)
    private String gmt;

    @Column(nullable = false)
    private String state; // active or deleted

    @Column(nullable = false)
    private String modified; // time when case edited

    public String getKey() { return key; }

    public void setKey(final String k) { this.key = k; }

    public String getContent() { return content; }

    public void setContent(String c) { this.content = c; }

    public String getGmt() { return gmt; }

    public void setGmt(String t) { this.gmt = t; }

    public String getState() { return state; }

    public void setState(String s) { this.state = s; }

    public String getModified() { return modified; }

    public void setModified(String m) { this.modified = m; }
}
