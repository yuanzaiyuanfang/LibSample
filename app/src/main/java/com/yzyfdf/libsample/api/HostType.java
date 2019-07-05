package com.yzyfdf.libsample.api;


/**
 * 域名管理
 */
public enum HostType {

    Industry(ApiConstant.industrry, 1),
    UserCenter(ApiConstant.userCenter, 2);

    public String mHost;
    public int mIndex;

    HostType(String host, int index) {
        mHost = host;
        mIndex = index;
    }
}
