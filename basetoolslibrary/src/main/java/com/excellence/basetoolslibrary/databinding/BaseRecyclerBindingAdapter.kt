package com.excellence.basetoolslibrary.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/14
 *     desc   : 开启dataBinding，RecyclerView通用适配器
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre>
 */
open class BaseRecyclerBindingAdapter<T> : MultiItemTypeBindingRecyclerAdapter<T> {

    private val mLayoutId: Int
    private val mVariableId: Int

    constructor(data: Array<T>?, @LayoutRes layoutId: Int, variableId: Int) : this(data, layoutId, variableId, null)

    constructor(data: Array<T>?, @LayoutRes layoutId: Int, variableId: Int,
                lifecycleOwner: LifecycleOwner?) : this(if (data == null) null else listOf(*data), layoutId, variableId, lifecycleOwner)

    constructor(data: List<T>?, @LayoutRes layoutId: Int, variableId: Int) : this(data, layoutId, variableId, null)

    constructor(data: List<T>?, @LayoutRes layoutId: Int, variableId: Int,
                lifecycleOwner: LifecycleOwner?) : super(data, lifecycleOwner) {
        mLayoutId = layoutId
        mVariableId = variableId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), mLayoutId, parent, false)
        return RecyclerViewHolder.getViewHolder(binding, mLifecycleOwner)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val binding = holder.getBinding()
        binding.setVariable(mVariableId, getItem(position))
        binding.executePendingBindings()
        setViewListener(holder, position)
    }
}