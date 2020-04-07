package com.yzyfdf.library.rx

import java.io.Serializable

class BaseRespose<T> : Serializable {
    var code: Int = 0
    var message: String? = null
    var data: T? = null

    fun success(): Boolean {
        return this.code == 0
    }

    override fun toString(): String {
        return "BaseRespose{" +
                "code=" + code +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }
}
