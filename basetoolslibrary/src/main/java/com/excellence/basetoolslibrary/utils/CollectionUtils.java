package com.excellence.basetoolslibrary.utils;

import java.util.Collection;
import java.util.Collections;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2021/7/2
 *     desc   : 集合
 * </pre> 
 */
public class CollectionUtils {

    /**
     * 清空集合里的空元素
     *
     * @param collection
     */
    public void removeEmptyElement(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        collection.removeAll(Collections.singleton(null));
    }

}
