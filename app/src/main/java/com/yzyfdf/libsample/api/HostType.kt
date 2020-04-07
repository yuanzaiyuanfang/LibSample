package com.yzyfdf.libsample.api


/**
 * 域名管理
 */
enum class HostType private constructor(var mHost: String, var mIndex: Int) {

    Industry(ApiConstant.industrry, 1),
    UserCenter(ApiConstant.userCenter, 2)
}
