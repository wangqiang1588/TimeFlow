package com.mobibrw.persist.api;

/**
 * Created by longsky on 2017/9/3.
 */

public class TimeInfo {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private long id;
    private String content;
}
