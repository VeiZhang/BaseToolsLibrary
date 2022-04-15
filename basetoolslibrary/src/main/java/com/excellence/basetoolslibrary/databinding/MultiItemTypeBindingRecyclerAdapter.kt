package com.excellence.basetoolslibrary.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager
import com.excellence.basetoolslibrary.helper.DataHelper
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/14
 *     desc   : 开启dataBinding，多种类型布局RecyclerView通用适配器
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre>
 */
open class MultiItemTypeBindingRecyclerAdapter<T> : RecyclerView.Adapter<RecyclerViewHolder>, DataHelper<T> {

    @JvmField
    val mLifecycleOwner: LifecycleOwner?

    @JvmField
    val mData: MutableList<T?> = ArrayList()
    private val mItemViewDelegateManager: ItemViewDelegateManager<T> = ItemViewDelegateManager()

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var mOnItemFocusChangeListener: OnItemFocusChangeListener? = null
    private var mOnItemKeyListener: OnItemKeyListener? = null
    private var mSelectedItemPosition = -1

    @JvmOverloads
    constructor(data: Array<T>?, lifecycleOwner: LifecycleOwner? = null) : this(if (data == null) null else listOf(*data), lifecycleOwner)

    @JvmOverloads
    constructor(data: List<T>?, lifecycleOwner: LifecycleOwner? = null) : super() {
        mLifecycleOwner = lifecycleOwner
        data?.let { mData.addAll(data) }
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    fun addItemViewDelegate(delegate: ItemViewDelegate<T>): MultiItemTypeBindingRecyclerAdapter<T> {
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
    fun addItemViewDelegate(viewType: Int, delegate: ItemViewDelegate<T>): MultiItemTypeBindingRecyclerAdapter<T> {
        mItemViewDelegateManager.addDelegate(viewType, delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    fun removeItemViewDelegate(delegate: ItemViewDelegate<T>?): MultiItemTypeBindingRecyclerAdapter<T> {
        mItemViewDelegateManager.removeDelegate(delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    fun removeItemViewDelegate(viewType: Int): MultiItemTypeBindingRecyclerAdapter<T> {
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

    override fun getItemViewType(position: Int): Int {
        return if (userItemViewDelegateManager()) {
            mItemViewDelegateManager.getItemViewType(mData[position], position)
        } else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        holder.markAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerViewHolder) {
        holder.markDetachedFromWindow()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutId, parent, false)
        return RecyclerViewHolder.getViewHolder(binding, mLifecycleOwner)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val delegate = getItemViewDelegate(getItemViewType(position))
        val binding = holder.getBinding()

        val item: T? = getItem(position)
        binding.setVariable(delegate!!.getItemVariable(), item)
        delegate.convert(binding, item, position)
        binding.executePendingBindings()
        setViewListener(holder, position)
    }

    protected open fun setViewListener(holder: RecyclerViewHolder, position: Int) {
        val binding = holder.getBinding()
        val itemView = binding.root

        /**
         * 如果执行了submitList增减，则当监听事件时，position就是错误的
         * 此时应该使用[RecyclerViewHolder.getAdapterPosition] 纠正
         */
        itemView.setOnClickListener { v ->
            mOnItemClickListener?.onItemClick(binding, v, holder.adapterPosition)
        }
        itemView.setOnLongClickListener { v ->
            (mOnItemLongClickListener?.onItemLongClick(binding, v, holder.adapterPosition) ?: false)
        }
        itemView.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val position = holder.adapterPosition
            mSelectedItemPosition = if (hasFocus) position else -1
            if (position >= 0) {
                mOnItemFocusChangeListener?.onItemFocusChange(binding, v, hasFocus, position)
            }
        }
        itemView.setOnKeyListener { v, keyCode, event ->
            (mOnItemKeyListener?.onKey(binding, v, keyCode, event, holder.adapterPosition)) ?: false
        }
    }

    open fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    open fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mOnItemLongClickListener = listener
    }

    open fun setOnItemFocusChangeListener(listener: OnItemFocusChangeListener) {
        mOnItemFocusChangeListener = listener
    }

    open fun setOnItemKeyListener(onItemKeyListener: OnItemKeyListener) {
        mOnItemKeyListener = onItemKeyListener
    }

    /**
     * 获取当前焦点位置
     *
     * @return -1表示没有焦点
     */
    fun getSelectedItemPosition(): Int {
        return mSelectedItemPosition
    }

    /**** 以下为辅助方法 ****/

    /**
     * 获取数据集
     *
     * @return
     */
    override val data: List<T?> = mData

    /**
     * 获取单个数据
     *
     * @param position
     * @return
     */
    override fun getItem(position: Int): T? {
        return mData[position]
    }

    /**
     * [RecyclerView.Adapter.notifyDataSetChanged]处理焦点问题
     *
     * @param data
     */
    open fun notifyData(data: List<T>?) {
        mData.clear()
        data?.let { mData.addAll(data) }
        notifyDataSetChanged()
    }

    /**
     * 新数据集替代旧数据集，刷新视图
     * [notifyDataSetChanged] 没有动画效果，刷新效率比不上下面方法（伴有动画效果：闪烁）
     * 位置不会刷新的方法，使用[.notifyItemRangeChanged]替代
     *
     * @see notifyItemChanged
     * @see notifyItemInserted
     * @see notifyItemRemoved
     * @see notifyItemRangeChanged
     * @see notifyItemMoved
     * @see notifyItemRangeInserted
     * @see notifyItemRangeRemoved
     *
     * @param data 新数据集
     */
    override fun notifyNewData(data: List<T>?) {
        notifyItemRangeRemoved(0, mData.size)
        mData.clear()
        data?.let { mData.addAll(data) }
        notifyItemRangeChanged(0, mData.size)
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
        data?.let { mData.addAll(position, data) }
        notifyItemRangeChanged(position, mData.size - position)
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
        notifyItemRangeChanged(position, mData.size - position)
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
        notifyItemChanged(position)
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
        notifyItemRemoved(position)
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
        val removeList: MutableList<T?> = ArrayList()
        for (i in startPosition..endPosition) {
            removeList.add(mData[i])
        }
        notifyItemRangeRemoved(startPosition, removeList.size)
        mData.removeAll(removeList)
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
        notifyItemMoved(fromPosition, toPosition)
    }

    /**
     * 移动位置，从fromPosition插入到toPosition
     * 1 2 3 4 -> 1 3 4 2
     *
     * @param fromPosition
     * @param toPosition
     */
    override fun move(fromPosition: Int, toPosition: Int) {
        var fromPosition = fromPosition
        var toPosition = toPosition
        if (fromPosition < 0 || fromPosition > mData.size - 1) {
            return
        }
        if (toPosition < 0 || toPosition > mData.size - 1) {
            return
        }
        if (fromPosition == toPosition) {
            return
        }
        val item = mData[fromPosition]
        mData.removeAt(fromPosition)
        mData.add(toPosition, item)
        val index = fromPosition
        fromPosition = min(index, toPosition)
        toPosition = max(index, toPosition)
        notifyItemRangeChanged(fromPosition, abs(toPosition - fromPosition) + 1)
    }

    /**
     * 清空数据集
     */
    override fun clear() {
        notifyItemRangeRemoved(0, mData.size)
        mData.clear()
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