package com.mabang.android.entity.vo;

@SuppressWarnings("serial")
public class BillboardImageInfo extends VoBase {

    private Long id;
    private Integer billboardId;
    private String key;
    private String imageUrl;
    private int imageType;//1:广告图片，2：验收图片
    private int type; // 0：更新 1.删除

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        return;
    }

    public Integer getBillboardId() {
        return this.billboardId;
    }

    public void setBillboardId(Integer billboardId) {
        this.billboardId = billboardId;
        return;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
        return;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
        return;
    }

    public int getImageType() {
        return this.imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
        return;
    }

}
