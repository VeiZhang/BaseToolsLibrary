package com.excellence.basetoolslibrary.baseadapter.base

import androidx.annotation.LayoutRes
import com.excellence.basetoolslibrary.baseadapter.ViewHolder

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : [com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter]
 *              多布局视图接口
 *              多布局使用时，多组数据集成基类，方便于判断是否使用特定视图接口
 *
 * </pre>
 */
interface ItemViewDelegate<T> {

    /**
     * 布局资源Id
     *
     * @return 布局Id
     */
    @LayoutRes
    fun getItemViewLayoutId(): Int

    /**
     * 判断视图是否使用该类布局
     *
     * @param item 数据
     * @param position 位置
     * @return `true`:是<br>`false`:否
     */
    fun isForViewType(item: T?, position: Int): Boolean

    /**
     * 初始化Item视图
     *
     * @param viewHolder
     * @param item 数据
     * @param position 位置
     */
    fun convert(viewHolder: ViewHolder, item: T?, position: Int)
}