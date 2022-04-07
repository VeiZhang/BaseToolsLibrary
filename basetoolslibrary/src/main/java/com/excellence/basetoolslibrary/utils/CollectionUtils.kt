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
        collection.removeAll(setOf<Any?>(null))
    }

}