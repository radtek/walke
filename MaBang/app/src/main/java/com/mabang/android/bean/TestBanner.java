package com.mabang.android.bean;

import java.io.Serializable;

/**
 * Created by walke.Z on 2017/8/11.
 */

public class TestBanner implements Serializable {

    private String title;
    private String url;

    public TestBanner() {
    }

    public TestBanner(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
