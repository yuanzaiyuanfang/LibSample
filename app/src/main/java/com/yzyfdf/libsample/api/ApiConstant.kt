package com.yzyfdf.libsample.api


import com.yzyfdf.libsample.BuildConfig

/**
 * @author sjj , 2019/4/25 17:15
 * //todo
 */
object ApiConstant {
    internal var industrry: String
    internal var userCenter: String

    //上传头像类型
    val avatar = "avatar"

    init {
        when (BuildConfig.env) {
            "test" -> {
                industrry = "http://114.55.172.160:28080/v1/"
                userCenter = "http://121.40.29.234:28081/v1/"
            }
            else -> {
                industrry = "xxxxxxxx"
                userCenter = "yyyyyyyyyy"
            }
        }
    }
}
