package com.excellence.basetoolslibrary.recycleradapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegate
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegateManager
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/13
 *     desc   : 多种类型布局RecyclerView [ListAdapter]通用适配器
 * </pre>
 */
open class MultiItemTypeRecyclerListAdapter<T> : ListAdapter<T, RecyclerViewHolder> {

    @JvmField
    val mData: MutableList<T?> = ArrayList()
    private val mItemViewDelegateManager: ItemViewDelegateManager<T> = ItemViewDelegateManager()

    private var mOnItemKeyListener: OnItemKeyListener? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var mOnItemFocusChangeListener: OnItemFocusChangeListener? = null
    private var mSelectedItemPosition = -1

    constructor(@NonNull diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)

    constructor(@NonNull config: AsyncDifferConfig<T>) : super(config)

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    fun addItemViewDelegate(delegate: ItemViewDelegate<T>): MultiItemTypeRecyclerListAdapter<T> {
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
    fun addItemViewDelegate(viewType: Int, delegate: ItemViewDelegate<T>): MultiItemTypeRecyclerListAdapter<T> {
        mItemViewDelegateManager.addDelegate(viewType, delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    fun removeItemViewDelegate(delegate: ItemViewDelegate<T>?): MultiItemTypeRecyclerListAdapter<T> {
        mItemViewDelegateManager.removeDelegate(delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    fun removeItemViewDelegate(viewType: Int): MultiItemTypeRecyclerListAdapter<T> {
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
            mItemViewDelegateManager.getItemViewType(getItem(position), position)
        } else super.getItemViewType(position)
    }

    /**
     * 开放接口，如果删除，则他是protected方法无法被调用
     */
    public override fun getItem(position: Int): T? {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType)
        return RecyclerViewHolder.getViewHolder(parent.context, parent, layoutId)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val delegate = getItemViewDelegate(getItemViewType(position))
        delegate!!.convert(holder, getItem(position), position)
        setViewListener(holder, position)
    }

    protected open fun setViewListener(holder: RecyclerViewHolder, position: Int) {
        val itemView = holder.getConvertView()

        /**
         * 如果执行了submitList增减，则当监听事件时，position就是错误的
         * 此时应该使用[RecyclerViewHolder.getAdapterPosition] 纠正
         */
        itemView.setOnClickListener { v ->
            mOnItemClickListener?.onItemClick(holder, v, holder.adapterPosition)
        }
        itemView.setOnLongClickListener { v ->
            (mOnItemLongClickListener?.onItemLongClick(holder, v, holder.adapterPosition) ?: false)
        }
        itemView.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val position = holder.adapterPosition
            mSelectedItemPosition = if (hasFocus) position else -1
            if (position >= 0) {
                mOnItemFocusChangeListener?.onItemFocusChange(holder, v, hasFocus, position)
            }
        }
        itemView.setOnKeyListener { v, keyCode, event ->
            (mOnItemKeyListener?.onKey(holder, v, keyCode, event, holder.adapterPosition)) ?: false
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