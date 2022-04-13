package com.excellence.basetoolslibrary.recycleradapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2016/12/20
 *     desc   : RecyclerView通用适配器
 * </pre>
 */
abstract class BaseRecyclerAdapter<T> : MultiItemTypeRecyclerAdapter<T> {

    private val mLayoutId: Int

    /**
     * @param data 数组数据源
     * @param layoutId 布局资源Id
     */
    constructor(data: Array<T>?, @LayoutRes layoutId: Int) : this(if (data == null) null else listOf(*data), layoutId)

    /**
     * @param data 列表数据源
     * @param layoutId 布局资源Id
     */
    constructor(data: List<T>?, @LayoutRes layoutId: Int) : super(data) {
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