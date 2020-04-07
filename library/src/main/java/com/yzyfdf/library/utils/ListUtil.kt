package com.yzyfdf.library.utils

/**
 * @author sjj , 2019/5/16 14:16
 */
object ListUtil {
    /**
     * list是否为空
     */
    fun isEmpty(list: List<*>?): Boolean {
        return list == null || list.isEmpty()
    }

    /**
     * 获取list第一个元素 可能为null
     */
    fun <T> getFirst(list: List<T>?): T? {
        return if (list == null || list.isEmpty()) {
            null
        } else list[0]
    }
}
