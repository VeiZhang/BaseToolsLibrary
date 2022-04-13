package com.excellence.basetoolslibrary.recycleradapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 单类型布局RecyclerView [ListAdapter]通用适配器，继承[ListAdapter]，使用内部的Diff
 * </pre>
 */
abstract class BaseRecyclerListAdapter<T> : MultiItemTypeRecyclerListAdapter<T> {

    private var mLayoutId: Int

    constructor(diffCallback: DiffUtil.ItemCallback<T>, @LayoutRes layoutId: Int) : super(diffCallback) {
        mLayoutId = layoutId
    }

    constructor(config: AsyncDifferConfig<T>, @LayoutRes layoutId: Int) : super(config) {
        mLayoutId = layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.getViewHolder(parent.context, parent, mLayoutId)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        convert(holder, getItem(position), position)
        setViewListener(holder, position)
    }

    abstract fun convert(viewHolder: RecyclerViewHolder, item: T?, position: Int)
}