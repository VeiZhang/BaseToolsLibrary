package com.excellence.basetoolslibrary.databinding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre>
 */
open class RecyclerViewHolder : RecyclerView.ViewHolder {

    val mBinding: ViewDataBinding

    constructor(dataBinding: ViewDataBinding) : super(dataBinding.root) {
        mBinding = dataBinding
    }

    companion object {

        @JvmStatic
        fun getViewHolder(binding: ViewDataBinding, lifecycleOwner: LifecycleOwner?): RecyclerViewHolder {
            return lifecycleOwner?.let {
                DataBoundViewHolder(binding, it)
            } ?: RecyclerViewHolder(binding)
        }
    }

    open fun getBinding(): ViewDataBinding {
        return mBinding
    }

    open fun markAttachedToWindow() {

    }

    open fun markDetachedFromWindow() {

    }

}