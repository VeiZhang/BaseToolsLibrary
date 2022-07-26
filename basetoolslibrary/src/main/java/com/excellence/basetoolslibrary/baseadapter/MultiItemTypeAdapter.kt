package com.excellence.basetoolslibrary.baseadapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegateManager
import com.excellence.basetoolslibrary.helper.DataHelper
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/12
 *     desc   :
 * </pre>
 */
open class MultiItemTypeAdapter<T>() : BaseAdapter(), DataHelper<T> {

    @JvmField
    val mData: MutableList<T?> = ArrayList()
    private val mItemViewDelegateManager: ItemViewDelegateManager<T> = ItemViewDelegateManager()

    constructor(data: List<T>?) : this() {
        data?.let { mData.addAll(it) }
    }

    constructor(data: Array<T>?) : this(if (data == null) null else listOf(*data))

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    fun addItemViewDelegate(delegate: ItemViewDelegate<T>): MultiItemTypeAdapter<T> {
        mItemViewDelegateManager.addDelegate(delegate)
        return this
    }

    /**
     * 添加视图
     *
     * @param viewType 布局类型
     * @param delegate 视图
     * @return
     */
    fun addItemViewDelegate(viewType: Int, delegate: ItemViewDelegate<T>): MultiItemTypeAdapter<T> {
        mItemViewDelegateManager.addDelegate(viewType, delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    fun removeItemViewDelegate(delegate: ItemViewDelegate<T>): MultiItemTypeAdapter<T> {
        mItemViewDelegateManager.removeDelegate(delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    fun removeItemViewDelegate(viewType: Int): MultiItemTypeAdapter<T> {
        mItemViewDelegateManager.removeDelegate(viewType)
        return this
    }

    /**
     * 获取视图
     *
     * @param viewType 布局类型
     * @return
     */
    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<T>? {
        return mItemViewDelegateManager.getItemViewDelegate(viewType)
    }

    /**
     * 判断视图是否可用
     *
     * @return `true`:是<br>`false`:否
     */
    private fun userItemViewDelegateManager(): Boolean {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0
    }

    /**
     * 获取视图数量
     *
     * @return 视图数量
     */
    override fun getViewTypeCount(): Int {
        return if (userItemViewDelegateManager()) {
            mItemViewDelegateManager.getItemViewDelegateCount()
        } else super.getViewTypeCount()
    }

    /**
     * 获取视图类型
     *
     * @param position 位置
     * @return 视图类型
     */
    override fun getItemViewType(position: Int): Int {
        return if (userItemViewDelegateManager()) {
            mItemViewDelegateManager.getItemViewType(getItem(position), position)
        } else super.getItemViewType(position)
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): T? {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val delegate = getItemViewDelegate(getItemViewType(position))
        val layoutId = delegate!!.getItemViewLayoutId()
        val viewHolder = ViewHolder.getViewHolder(parent.context, convertView, parent, layoutId)
        delegate.convert(viewHolder, getItem(position), position)
        return viewHolder.getConvertView()
    }

    /**** 以下为辅助方法  */

    override val data: List<T?> = mData

    /**
     * 新数据集替代旧数据集，刷新视图
     *
     * @param data 新数据集
     */
    override fun notifyNewData(data: List<T>?) {
        mData.clear()
        data?.let { mData.addAll(it) }
        notifyDataSetChanged()
    }

    /**
     * 新增数据集
     *
     * @param data 新数据集
     */
    override fun addAll(data: List<T>?) {
        addAll(mData.size, data)
    }

    /**
     * 插入新数据集
     *
     * @param position 插入位置
     * @param data 新数据集
     */
    override fun addAll(position: Int, data: List<T>?) {
        var position = position
        when {
            position < 0 -> {
                position = 0
            }
            position > mData.size -> {
                position = mData.size
            }
        }

        data?.let { mData.addAll(position, it) }
        notifyDataSetChanged()
    }

    /**
     * 新增数据
     *
     * @param item 数据
     */
    override fun add(item: T?) {
        add(mData.size, item)
    }


    /**
     * 插入新数据
     *
     * @param position 插入位置
     * @param item 数据
     */
    override fun add(position: Int, item: T?) {
        var position = position
        when {
            position < 0 -> {
                position = 0
            }
            position > mData.size -> {
                position = mData.size
            }
        }
        mData.add(position, item)
        notifyDataSetChanged()
    }

    /**
     * 修改源数据
     *
     * @param item 数据集中的对象，修改复杂类型（非基本类型）里面的变量值
     */
    override fun modify(item: T?) {
        modify(mData.indexOf(item), item)
    }

    /**
     * 替换数据
     *
     * @param position 替换位置
     * @param item 替换数据
     */
    override fun modify(position: Int, item: T?) {
        if (position < 0 || position > mData.size - 1) {
            return
        }
        mData[position] = item
        notifyDataSetChanged()
    }

    /**
     * 替换数据
     *
     * @param oldItem 被替换数据
     * @param newItem 替换数据
     */
    override fun modify(oldItem: T?, newItem: T?) {
        modify(mData.indexOf(oldItem), newItem)
    }

    /**
     * 删除数据
     *
     * @param item 被删除数据
     */
    override fun remove(item: T?) {
        remove(mData.indexOf(item))
    }

    /**
     * 删除数据
     *
     * @param position 删除位置
     */
    override fun remove(position: Int) {
        if (position < 0 || position > mData.size - 1) {
            return
        }
        mData.removeAt(position)
        notifyDataSetChanged()
    }

    /**
     * 批量删除
     *
     * @param startPosition 起始位置
     * @param endPosition 结束位置
     */
    override fun remove(startPosition: Int, endPosition: Int) {
        var startPosition = startPosition
        var endPosition = endPosition
        if (startPosition < 0 || startPosition > mData.size - 1) {
            return
        }
        if (endPosition < 0 || endPosition > mData.size - 1) {
            return
        }
        val index = startPosition
        startPosition = min(index, endPosition)
        endPosition = max(index, endPosition)
        val removeList: MutableList<T?> = java.util.ArrayList()
        for (i in startPosition..endPosition) {
            removeList.add(mData[i])
        }
        mData.removeAll(removeList)
        notifyDataSetChanged()
    }

    /**
     * 交换位置，fromPosition与toPosition交换
     * 1 2 3 4 -> 1 4 3 2
     *
     * @param fromPosition
     * @param toPosition
     */
    override fun swap(fromPosition: Int, toPosition: Int) {
        if (fromPosition < 0 || fromPosition > mData.size - 1) {
            return
        }
        if (toPosition < 0 || toPosition > mData.size - 1) {
            return
        }
        if (fromPosition == toPosition) {
            return
        }
        Collections.swap(mData, fromPosition, toPosition)
        notifyDataSetChanged()
    }

    /**
     * 移动位置，从fromPosition插入到toPosition
     * 1 2 3 4 -> 1 3 4 2
     *
     * @param fromPosition
     * @param toPosition
     */
    override fun move(fromPosition: Int, toPosition: Int) {
        if (fromPosition < 0 || fromPosition > mData.size - 1) {
            return
        }
        if (toPosition < 0 || toPosition > mData.size - 1) {
            return
        }
        if (fromPosition == toPosition) {
            return
        }
        val item: T? = mData[fromPosition]
        mData.removeAt(fromPosition)
        mData.add(toPosition, item)
        notifyDataSetChanged()
    }

    /**
     * 清空数据集
     */
    override fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    /**
     * 判断数据集是否包含数据
     *
     * @param item 待检测数据
     * @return `true`:包含<br>`false`: 不包含
     */
    override fun contains(item: T?): Boolean {
        return mData.contains(item)
    }
}