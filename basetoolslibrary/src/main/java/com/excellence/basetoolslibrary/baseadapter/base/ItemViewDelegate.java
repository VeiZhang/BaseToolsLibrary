package com.excellence.basetoolslibrary.baseadapter.base;

import android.support.annotation.LayoutRes;

import com.excellence.basetoolslibrary.baseadapter.ViewHolder;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : {@link com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter}
 *     			多布局视图接口
 *     			多布局使用时，多组数据集成基类，方便于判断是否使用特定视图接口
 *
 * </pre>
 */

public interface ItemViewDelegate<T> {

    /**
     * 布局资源Id
     *
     * @return 布局Id
     */
    @LayoutRes
    int getItemViewLayoutId();

    /**
     * 判断视图是否使用该类布局
     *
     * @param item 数据
     * @param position 位置
     * @return {@code true}:是<br>{@code false}:否
     */
    boolean isForViewType(T item, int position);

    /**
     * 初始化Item视图
     *
     * @param viewHolder
     * @param item 数据
     * @param position 位置
     */
    void convert(ViewHolder viewHolder, T item, int position);
}
