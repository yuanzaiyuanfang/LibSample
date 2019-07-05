package com.yzyfdf.libsample.api;


import com.yzyfdf.libsample.BuildConfig;

/**
 * @author sjj , 2019/4/25 17:15
 * //todo
 */
public class ApiConstant {
    static String industrry;
    static String userCenter;
    public static String shareH5;

    static {
        switch (BuildConfig.env) {
            case "test":
                industrry = "http://114.55.172.160:28080/v1/";
                userCenter = "http://121.40.29.234:28081/v1/";
                shareH5 = "http://192.168.1.119:8080/webview?url=";
                break;
            case "beta":
                industrry = "xxxxxxxx";
                userCenter = "yyyyyyyyyy";
                break;
        }
    }

    //logo 网络地址
    public static final String default_immage = "https://oss.neets.cc/android/logo/gytt.png";

    //上传头像类型
    public static final String avatar = "avatar";
}
