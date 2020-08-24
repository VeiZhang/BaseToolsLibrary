package com.excellence.basetoolslibrary.databinding.base;

import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/6
 *     desc   : binding可能需要单独的convertItemView
 * </pre> 
 */
public abstract class ItemViewDelegateImp<T> implements ItemViewDelegate<T> {

    /**
     * 如果有额外的处理
     *
     * @param binding
     * @param item 数据
     * @param position 位置
     */
    @Override
    public void convert(ViewDataBinding binding, T item, int position) {

    }
}
