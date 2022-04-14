package com.excellence.basetoolslibrary.baseadapter.base

import androidx.collection.SparseArrayCompat
import com.excellence.basetoolslibrary.baseadapter.ViewHolder

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2022/4/13
 *     desc   : [com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter]
 *     			多布局视图管理器
 *              默认0，1，2，3...为视图类型，且每个类型唯一；非位置标志
 * </pre>
 */
class ItemViewDelegateManager<T> {

    private val mDelegates: SparseArrayCompat<ItemViewDelegate<T>> = SparseArrayCompat<ItemViewDelegate<T>>()

    /**
     * 获取视图数量
     *
     * @return 视图数量
     */
    fun getItemViewDelegateCount(): Int {
        return mDelegates.size()
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    fun addDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        val viewType = mDelegates.size()
        if (delegate != null) {
            mDelegates.put(viewType, delegate)
        }
        return this
    }

    /**
     * 添加视图
     *
     * @param viewType 布局类型
     * @param delegate 视图
     * @return
     */
    fun addDelegate(viewType: Int, delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        require(mDelegates[viewType] == null) { "An ItemViewDelegate is already registered for the viewType = $viewType. Already registered ItemViewDelegate is ${mDelegates[viewType]}." }
        mDelegates.put(viewType, delegate)
        return this
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    fun removeDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        if (delegate != null) {
            val indexToRemove = mDelegates.indexOfValue(delegate)
            if (indexToRemove >= 0) {
                mDelegates.removeAt(indexToRemove)
            }
        }
        return this
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    fun removeDelegate(viewType: Int): ItemViewDelegateManager<T> {
        val indexToRemove = mDelegates.indexOfKey(viewType)
        if (indexToRemove >= 0) {
            mDelegates.removeAt(indexToRemove)
        }
        return this
    }

    /**
     * 获取视图类型
     *
     * @param item 数据
     * @param position 位置
     * @return
     */
    fun getItemViewType(item: T?, position: Int): Int {
        for (i in 0 until mDelegates.size()) {
            val delegate = mDelegates.valueAt(i)
            if (delegate.isForViewType(item, position)) {
                return mDelegates.keyAt(i)
            }
        }
        throw java.lang.IllegalArgumentException("No ItemViewDelegate added that matches position=$position in data source")
    }

    /**
     * 初始化Item视图
     *
     * @param viewHolder
     * @param item 数据
     * @param position 位置
     */
    fun convert(viewHolder: ViewHolder, item: T?, position: Int) {
        for (i in 0 until mDelegates.size()) {
            val delegate = mDelegates.valueAt(i)
            if (delegate.isForViewType(item, position)) {
                delegate.convert(viewHolder, item, position)
                return
            }
        }
        throw java.lang.IllegalArgumentException("No ItemViewDelegateManager added that matches position=$position in data source")
    }

    /**
     * 获取布局Id
     *
     * @param viewType 布局类型
     * @return 布局资源Id
     */
    fun getItemViewLayoutId(viewType: Int): Int {
        return mDelegates[viewType]!!.getItemViewLayoutId()
    }

    /**
     * 获取布局Id
     *
     * @param item 数据
     * @param position 位置
     * @return 布局资源Id
     */
    fun getItemViewLayoutId(item: T?, position: Int): Int {
        return getItemViewDelegate(item, position).getItemViewLayoutId()
    }

    /**
     * 获取视图
     *
     * @param viewType 布局类型
     * @return 视图
     */
    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<T>? {
        return mDelegates[viewType]
    }

    /**
     * 获取视图
     *
     * @param item 数据
     * @param position 位置
     * @return 视图
     */
    fun getItemViewDelegate(item: T?, position: Int): ItemViewDelegate<T> {
        for (i in 0 until mDelegates.size()) {
            val delegate = mDelegates.valueAt(i)
            if (delegate.isForViewType(item, position)) {
                return delegate
            }
        }
        throw java.lang.IllegalArgumentException("No ItemViewDelegate added that matches position=$position in data source")
    }

    /**
     * 获取视图类型
     *
     * @param itemViewDelegate 视图
     * @return 视图类型
     */
    fun getItemViewType(itemViewDelegate: ItemViewDelegate<T>?): Int {
        return mDelegates.indexOfValue(itemViewDelegate)
    }
}