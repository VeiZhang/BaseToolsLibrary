package com.excellence.basetoolslibrary.databinding.base;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/16
 *     desc   : {@link com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingAdapter}
 *                {@link com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter}
 *              多布局视图接口
 *     			多布局使用时，多组数据集成基类，方便于判断是否使用特定视图接口
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
     * ViewDataBinding的设置项{@link androidx.databinding.ViewDataBinding#setVariable(int, Object)}
     * 可搭配{@link #convert}
     *
     * @return variableId
     */
    int getItemVariable();

    /**
     * 初始化Item视图，可能需要单独处理
     *
     * @param binding
     * @param item 数据
     * @param position 位置
     */
    void convert(ViewDataBinding binding, T item, int position);
}
