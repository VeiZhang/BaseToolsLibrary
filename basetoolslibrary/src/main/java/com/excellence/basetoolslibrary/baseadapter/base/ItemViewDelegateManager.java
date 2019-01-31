package com.excellence.basetoolslibrary.baseadapter.base;

import android.support.v4.util.SparseArrayCompat;

import com.excellence.basetoolslibrary.baseadapter.ViewHolder;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : {@link com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter}
 *     			多布局视图管理器
 *              默认0，1，2，3...为视图类型，且每个类型唯一；非位置标志
 * </pre>
 */

public class ItemViewDelegateManager<T> {

    private SparseArrayCompat<ItemViewDelegate<T>> mDelegates = new SparseArrayCompat<>();

    /**
     * 获取视图数量
     *
     * @return 视图数量
     */
    public int getItemViewDelegateCount() {
        return mDelegates.size();
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = mDelegates.size();
        if (delegate != null) {
            mDelegates.put(viewType, delegate);
        }
        return this;
    }

    /**
     * 添加视图
     *
     * @param viewType 布局类型
     * @param delegate 视图
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (mDelegates.get(viewType) != null) {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = " + viewType + ". Already registered ItemViewDelegate is " + mDelegates.get(viewType));
        }
        mDelegates.put(viewType, delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate != null) {
            int indexToRemove = mDelegates.indexOfValue(delegate);
            mDelegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(int viewType) {
        int indexToRemove = mDelegates.indexOfKey(viewType);
        if (indexToRemove >= 0) {
            mDelegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 获取视图类型
     *
     * @param item 数据
     * @param position 位置
     * @return
     */
    public int getItemViewType(T item, int position) {
        for (int i = 0; i < mDelegates.size(); i++) {
            ItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return mDelegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 初始化Item视图
     *
     * @param viewHolder
     * @param item 数据
     * @param position 位置
     */
    public void convert(ViewHolder viewHolder, T item, int position) {
        for (int i = 0; i < mDelegates.size(); i++) {
            ItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                delegate.convert(viewHolder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 获取布局Id
     *
     * @param viewType 布局类型
     * @return 布局资源Id
     */
    public int getItemViewLayoutId(int viewType) {
        return mDelegates.get(viewType).getItemViewLayoutId();
    }

    /**
     * 获取布局Id
     *
     * @param item 数据
     * @param position 位置
     * @return 布局资源Id
     */
    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }

    /**
     * 获取视图
     *
     * @param viewType 布局类型
     * @return 视图
     */
    public ItemViewDelegate<T> getItemViewDelegate(int viewType) {
        return mDelegates.get(viewType);
    }

    /**
     * 获取视图
     *
     * @param item 数据
     * @param position 位置
     * @return 视图
     */
    public ItemViewDelegate<T> getItemViewDelegate(T item, int position) {
        for (int i = 0; i < mDelegates.size(); i++) {
            ItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 获取视图类型
     *
     * @param itemViewDelegate 视图
     * @return 视图类型
     */
    public int getItemViewType(ItemViewDelegate<T> itemViewDelegate) {
        return mDelegates.indexOfValue(itemViewDelegate);
    }

}
