package com.yzyfdf.library.utils;

import java.util.List;

/**
 * @author sjj , 2019/5/16 14:16
 */
public class ListUtil {
    /**
     * list是否为空
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 获取list第一个元素 可能为null
     */
    public static <T> T getFirst(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
