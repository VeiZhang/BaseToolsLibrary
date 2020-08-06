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
public class ItemViewDelegateImp<T> implements ItemViewDelegate<T> {

    @Override
    public int getItemViewLayoutId() {
        return 0;
    }

    @Override
    public boolean isForViewType(T item, int position) {
        return false;
    }

    @Override
    public int getItemVariable() {
        return 0;
    }

    @Override
    public void convert(ViewDataBinding binding, T item, int position) {

    }
}
