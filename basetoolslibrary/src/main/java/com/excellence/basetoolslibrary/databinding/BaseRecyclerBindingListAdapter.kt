package com.excellence.basetoolslibrary.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.excellence.basetoolslibrary.databinding.RecyclerViewHolder.Companion.getViewHolder

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/14
 *     desc   : 开启dataBinding，单类型布局RecyclerView [ListAdapter]通用适配器，继承[ListAdapter]，使用内部的Diff
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre>
 */
open class BaseRecyclerBindingListAdapter<T> : MultiItemTypeBindingRecyclerListAdapter<T> {

    private val mLayoutId: Int
    private val mVariableId: Int

    @JvmOverloads
    constructor(diffCallback: DiffUtil.ItemCallback<T>, @LayoutRes layoutId: Int,
                variableId: Int, lifecycleOwner: LifecycleOwner? = null) : super(diffCallback, lifecycleOwner) {
        mLayoutId = layoutId
        mVariableId = variableId
    }

    @JvmOverloads
    constructor(config: AsyncDifferConfig<T>, @LayoutRes layoutId: Int,
                variableId: Int, lifecycleOwner: LifecycleOwner? = null) : super(config, lifecycleOwner) {
        mLayoutId = layoutId
        mVariableId = variableId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), mLayoutId, parent, false)
        return getViewHolder(binding, mLifecycleOwner)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val binding = holder.getBinding()
        binding.setVariable(mVariableId, getItem(position))
        binding.executePendingBindings()
        setViewListener(holder, position)
    }
}