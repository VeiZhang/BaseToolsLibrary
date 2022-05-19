package com.excellence.basetoolslibrary.utils

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/6
 *     desc   :
 * </pre>
 */
object CollectionUtils {

    /**
     * 清空集合里的空元素
     */
    @JvmStatic
    fun removeEmptyElement(collection: MutableCollection<*>) {
        collection.remove(null)
    }

    /**
     * 比较两个列表元素对象是否一致
     *
     * @param list1
     * @param list2
     * @return
     */
    @JvmStatic
    fun listEquals(list1: List<*>?, list2: List<*>?): Boolean {
        if (list1 == null && list2 == null) {
            return true
        } else if (list1 == null || list2 == null) {
            return false
        }
        if (list1.size != list2.size) {
            return false
        } else {
            for (i in list1.indices) {
                if (list1[i] !== list2[i]) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 比较两个列表元素内容是否一致
     *
     * @param list1
     * @param list2
     * @return
     */
    @JvmStatic
    fun listContentEquals(list1: List<*>?, list2: List<*>?): Boolean {
        if (list1 == null && list2 == null) {
            return true
        } else if (list1 == null || list2 == null) {
            return false
        }
        if (list1.size != list2.size) {
            return false
        } else {
            for (i in list1.indices) {
                if (list1[i] != list2[i]) {
                    return false
                }
            }
        }
        return true
    }
}