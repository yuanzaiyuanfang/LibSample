package com.yzyfdf.libsample.model.bean;

/**
 * @author sjj , 2019/4/29 14:40
 * 上传图片返回
 */
public class UploadImageBean {

    /**
     * imageUrl : xxx
     */

    private String imageUrl;
    private String origFileName;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrigFileName() {
        return origFileName;
    }

    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }
}
