package com.excellence.basetoolslibrary.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import java.util.*

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/14
 *     desc   : 开启dataBinding，多种类型布局RecyclerView {@link ListAdapter}通用适配器，继承{@link ListAdapter}，使用内部的Diff
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre>
 */
open class MultiItemTypeBindingRecyclerListAdapter<T> : ListAdapter<T, RecyclerViewHolder> {

    @JvmField
    val mLifecycleOwner: LifecycleOwner?

    @JvmField
    val mData: MutableList<T?> = ArrayList()
    private val mItemViewDelegateManager: ItemViewDelegateManager<T> = ItemViewDelegateManager()

    private var mOnItemKeyListener: OnItemKeyListener? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var mOnItemFocusChangeListener: OnItemFocusChangeListener? = null
    private var mSelectedItemPosition = -1

    @JvmOverloads
    constructor(diffCallback: DiffUtil.ItemCallback<T>, lifecycleOwner: LifecycleOwner? = null) : super(diffCallback) {
        mLifecycleOwner = lifecycleOwner
    }

    @JvmOverloads
    constructor(config: AsyncDifferConfig<T>, lifecycleOwner: LifecycleOwner? = null) : super(config) {
        mLifecycleOwner = lifecycleOwner
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    fun addItemViewDelegate(delegate: ItemViewDelegate<T>): MultiItemTypeBindingRecyclerListAdapter<T> {
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
    fun addItemViewDelegate(viewType: Int, delegate: ItemViewDelegate<T>): MultiItemTypeBindingRecyclerListAdapter<T> {
        mItemViewDelegateManager.addDelegate(viewType, delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    fun removeItemViewDelegate(delegate: ItemViewDelegate<T>?): MultiItemTypeBindingRecyclerListAdapter<T> {
        mItemViewDelegateManager.removeDelegate(delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    fun removeItemViewDelegate(viewType: Int): MultiItemTypeBindingRecyclerListAdapter<T> {
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

    /**
     * 开放接口，如果删除，则他是protected方法无法被调用
     */
    public override fun getItem(position: Int): T? {
        return super.getItem(position)
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

    override fun submitList(list: MutableList<T>?) {
        if (isEmpty(list)) {
            /**
             * 当list为空或者size为0时，使用null清空快速
             */
            mData.clear()
            super.submitList(null)
        } else {
            mData.addAll(list!!)
            super.submitList(list)
        }
    }

    open fun getData(): List<T?> {
        return mData
    }
}