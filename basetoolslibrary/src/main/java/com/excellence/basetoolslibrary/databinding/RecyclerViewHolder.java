package com.excellence.basetoolslibrary.databinding;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre> 
 */

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    protected ViewDataBinding mBinding = null;

    public RecyclerViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
        mBinding = dataBinding;
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }
}