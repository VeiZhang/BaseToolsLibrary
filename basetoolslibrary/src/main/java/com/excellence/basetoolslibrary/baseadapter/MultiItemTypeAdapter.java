package com.excellence.basetoolslibrary.baseadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegateManager;
import com.excellence.basetoolslibrary.helper.DataHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : 多种类型ListView、GridView通用适配器
 * </pre>
 */

public class MultiItemTypeAdapter<T> extends BaseAdapter implements DataHelper<T> {

    protected List<T> mData = new ArrayList<>();
    private ItemViewDelegateManager<T> mItemViewDelegateManager;

    public MultiItemTypeAdapter(T[] data) {
        this(data == null ? null : Arrays.asList(data));
    }

    public MultiItemTypeAdapter(List<T> data) {
        if (data != null) {
            mData.addAll(data);
        }
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addDelegate(delegate);
        return this;
    }

    /**
     * 添加视图
     *
     * @param viewType 布局类型
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addDelegate(viewType, delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.removeDelegate(delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    public MultiItemTypeAdapter<T> removeItemViewDelegate(int viewType) {
        mItemViewDelegateManager.removeDelegate(viewType);
        return this;
    }

    /**
     * 获取视图
     *
     * @param viewType 布局类型
     * @return
     */
    public ItemViewDelegate<T> getItemViewDelegate(int viewType) {
        return mItemViewDelegateManager.getItemViewDelegate(viewType);
    }

    /**
     * 判断视图是否可用
     *
     * @return {@code true}:是<br>{@code false}:否
     */
    private boolean userItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * 获取视图数量
     *
     * @return 视图数量
     */
    @Override
    public int getViewTypeCount() {
        if (userItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    /**
     * 获取视图类型
     *
     * @param position 位置
     * @return 视图类型
     */
    @Override
    public int getItemViewType(int position) {
        if (userItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate<T> delegate = getItemViewDelegate(getItemViewType(position));
        int layoutId = delegate.getItemViewLayoutId();
        ViewHolder viewHolder = ViewHolder.getViewHolder(parent.getContext(), convertView, parent, layoutId);
        delegate.convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /**** 以下为辅助方法 ****/

    @Override
    public List<T> getData() {
        return mData;
    }

    /**
     * 新数据集替代旧数据集，刷新视图
     *
     * @param data 新数据集
     */
    @Override
    public void notifyNewData(List<T> data) {
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 新增数据集
     *
     * @param data 新数据集
     */
    @Override
    public void addAll(List<T> data) {
        addAll(mData.size(), data);
    }

    /**
     * 插入新数据集
     *
     * @param position 插入位置
     * @param data 新数据集
     */
    @Override
    public void addAll(int position, List<T> data) {
        if (position < 0) {
            position = 0;
        }
        if (position > mData.size()) {
            position = mData.size();
        }
        if (data != null) {
            mData.addAll(position, data);
        }
        notifyDataSetChanged();
    }

    /**
     * 新增数据
     *
     * @param item 数据
     */
    @Override
    public void add(T item) {
        add(mData.size(), item);
    }

    /**
     * 插入新数据
     *
     * @param position 插入位置
     * @param item 数据
     */
    @Override
    public void add(int position, T item) {
        if (position < 0) {
            position = 0;
        }
        if (position > mData.size()) {
            position = mData.size();
        }
        mData.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * 修改源数据
     *
     * @param item 数据集中的对象，修改复杂类型（非基本类型）里面的变量值
     */
    @Override
    public void modify(T item) {
        modify(mData.indexOf(item), item);
    }

    /**
     * 替换数据
     *
     * @param position 替换位置
     * @param item 替换数据
     */
    @Override
    public void modify(int position, T item) {
        if (position < 0 || position > mData.size() - 1) {
            return;
        }
        mData.set(position, item);
        notifyDataSetChanged();
    }

    /**
     * 替换数据
     *
     * @param oldItem 被替换数据
     * @param newItem 替换数据
     */
    @Override
    public void modify(T oldItem, T newItem) {
        modify(mData.indexOf(oldItem), newItem);
    }

    /**
     * 删除数据
     *
     * @param item 被删除数据
     */
    @Override
    public void remove(T item) {
        remove(mData.indexOf(item));
    }

    /**
     * 删除数据
     *
     * @param position 删除位置
     */
    @Override
    public void remove(int position) {
        if (position < 0 || position > mData.size() - 1) {
            return;
        }
        mData.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 批量删除
     *
     * @param startPosition 起始位置
     * @param endPosition 结束位置
     */
    @Override
    public void remove(int startPosition, int endPosition) {
        if (startPosition < 0 || startPosition > mData.size() - 1) {
            return;
        }
        if (endPosition < 0 || endPosition > mData.size() - 1) {
            return;
        }

        int index = startPosition;
        startPosition = Math.min(index, endPosition);
        endPosition = Math.max(index, endPosition);
        List<T> removeList = new ArrayList<>();
        for (int i = startPosition; i <= endPosition; i++) {
            removeList.add(mData.get(i));
        }
        mData.removeAll(removeList);
        notifyDataSetChanged();
    }

    /**
     * 交换位置，fromPosition与toPosition交换
     * 1 2 3 4 -> 1 4 3 2
     *
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void swap(int fromPosition, int toPosition) {
        if (fromPosition < 0 || fromPosition > mData.size() - 1) {
            return;
        }
        if (toPosition < 0 || toPosition > mData.size() - 1) {
            return;
        }
        if (fromPosition == toPosition) {
            return;
        }
        Collections.swap(mData, fromPosition, toPosition);
        notifyDataSetChanged();
    }

    /**
     * 移动位置，从fromPosition插入到toPosition
     * 1 2 3 4 -> 1 3 4 2
     *
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void move(int fromPosition, int toPosition) {
        if (fromPosition < 0 || fromPosition > mData.size() - 1) {
            return;
        }
        if (toPosition < 0 || toPosition > mData.size() - 1) {
            return;
        }
        if (fromPosition == toPosition) {
            return;
        }
        T item = mData.get(fromPosition);
        mData.remove(fromPosition);
        mData.add(toPosition, item);
        notifyDataSetChanged();
    }

    /**
     * 清空数据集
     */
    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 判断数据集是否包含数据
     *
     * @param item 待检测数据
     * @return {@code true}:包含<br>{@code false}: 不包含
     */
    @Override
    public boolean contains(T item) {
        return mData != null && mData.contains(item);
    }
}
