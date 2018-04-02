package com.mabang.android.okhttp.loadapk;

/**
 * Created by caihui on 2016/10/9.
 */
public class FileBean {

    private String localPath;
    private String loadUrl;

    public FileBean() {
    }

    public FileBean(String localPath, String loadUrl) {
        this.localPath = localPath;
        this.loadUrl = loadUrl;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "localPath='" + localPath + '\'' +
                ", loadUrl='" + loadUrl + '\'' +
                '}';
    }
}
