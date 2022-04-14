package com.excellence.basetoolslibrary.databinding.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/16
 *     desc   : [com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingAdapter]
 *              [com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter]
 *              多布局视图接口
 *     			多布局使用时，多组数据集成基类，方便于判断是否使用特定视图接口
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
     * ViewDataBinding的设置项[androidx.databinding.ViewDataBinding.setVariable]
     * 可搭配[convert]
     *
     * @return variableId
     */
    fun getItemVariable(): Int

    /**
     * 初始化Item视图，可能需要单独处理
     *
     * @param binding
     * @param item 数据
     * @param position 位置
     */
    fun convert(binding: ViewDataBinding, item: T?, position: Int)
}