package com.excellence.basetoolslibrary.baseadapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/13
 *     desc   : ListView、GridView通用适配器
 * </pre>
 */
abstract class CommonAdapter<T> : MultiItemTypeAdapter<T> {

    private val mLayoutId: Int

    constructor(data: Array<T>?, @LayoutRes layoutId: Int) : this(if (data == null) null else listOf(*data), layoutId)

    constructor(data: List<T>?, @LayoutRes layoutId: Int) : super(data) {
        mLayoutId = layoutId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = ViewHolder.getViewHolder(parent.context, convertView, parent, mLayoutId)
        convert(viewHolder, getItem(position), position)
        return viewHolder.getConvertView()
    }

    abstract fun convert(viewHolder: ViewHolder, item: T?, position: Int)

}