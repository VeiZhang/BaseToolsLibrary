package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter.RecyclerViewHolder;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;
import com.excellence.basetoolslibrary.helper.DataHelper;
import com.excellence.basetoolslibrary.helper.ViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   : 开启dataBinding，多种类型布局RecyclerView通用适配器
 * </pre>
 */

public class MultiItemTypeBindingRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements DataHelper<T> {

    protected List<T> mData = new ArrayList<>();
    private ItemViewDelegateManager<T> mItemViewDelegateManager = null;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    private OnItemFocusChangeListener mOnItemFocusChangeListener = null;
    private OnItemKeyListener mOnItemKeyListener = null;
    private int mSelectedItemPosition = -1;

    public MultiItemTypeBindingRecyclerAdapter(T[] data) {
        this(data == null ? null : Arrays.asList(data));
    }

    public MultiItemTypeBindingRecyclerAdapter(List<T> data) {
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
    public MultiItemTypeBindingRecyclerAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate) {
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
    public MultiItemTypeBindingRecyclerAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addDelegate(viewType, delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeBindingRecyclerAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.removeDelegate(delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    public MultiItemTypeBindingRecyclerAdapter<T> removeItemViewDelegate(int viewType) {
        mItemViewDelegateManager.removeDelegate(viewType);
        return this;
    }

    /**
     * 判断视图是否可用
     *
     * @return {@code true}:是<br>{@code false}:否
     */
    private boolean userItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (userItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(getItemViewType(position));
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(delegate.getItemVariable(), mData.get(position));
        binding.executePendingBindings();
        setViewListener(binding, position);
    }

    protected void setViewListener(final ViewDataBinding binding, final int position) {
        View itemView = binding.getRoot();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(binding, v, position);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mOnItemLongClickListener != null && mOnItemLongClickListener.onItemLongClick(binding, v, position);
            }
        });

        itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mSelectedItemPosition = hasFocus ? position : -1;
                if (mOnItemFocusChangeListener != null) {
                    mOnItemFocusChangeListener.onItemFocusChange(binding, v, hasFocus, position);
                }
            }
        });

        itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return mOnItemKeyListener != null && mOnItemKeyListener.onKey(binding, v, keyCode, event, position);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(ViewDataBinding binding, View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(ViewDataBinding binding, View v, int position);
    }

    public interface OnItemFocusChangeListener {
        void onItemFocusChange(ViewDataBinding binding, View v, boolean hasFocus, int position);
    }

    public interface OnItemKeyListener {
        boolean onKey(ViewDataBinding binding, View v, int keyCode, KeyEvent event, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public void setOnItemFocusChangeListener(OnItemFocusChangeListener listener) {
        mOnItemFocusChangeListener = listener;
    }

    public void setOnItemKeyListener(OnItemKeyListener onItemKeyListener) {
        mOnItemKeyListener = onItemKeyListener;
    }

    /**
     * 获取当前焦点位置
     *
     * @return -1表示没有焦点
     */
    public int getSelectedItemPosition() {
        return mSelectedItemPosition;
    }

    /**** 以下为辅助方法 ****/

    /**
     * 获取数据集
     *
     * @return
     */
    @Override
    public List<T> getData() {
        return mData;
    }

    /**
     * 获取单个数据
     *
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    /**
     * 新数据集替代旧数据集，刷新视图
     * {@link #notifyDataSetChanged()} 没有动画效果，刷新效率比不上下面方法（伴有动画效果：闪烁）
     * 位置不会刷新的方法，使用{@link #notifyItemRangeChanged}替代
     *
     * @see #notifyItemChanged(int) ：列表position位置刷新
     * @see #notifyItemInserted(int) ：列表position位置添加一条数据，位置不会刷新，要执行{@link #notifyItemRangeChanged}
     * @see #notifyItemRemoved(int) ：列表position位置移除一条数据，位置会刷新，不用执行{@link #notifyItemRangeChanged}
     * @see #notifyItemRangeChanged(int, int) ：列表从positionStart位置到itemCount数量的列表项进行数据刷新
     * @see #notifyItemMoved(int, int) ：列表fromPosition位置的数据移到toPosition位置，位置会刷新，不用执行{@link #notifyItemRangeChanged(int, int)}
     * @see #notifyItemRangeInserted(int, int) ：列表从positionStart位置到itemCount数量的列表项批量添加数据，位置不会刷新，要执行{@link #notifyItemRangeChanged(int, int)}
     * @see #notifyItemRangeRemoved(int, int) ：列表从positionStart位置到itemCount数量的列表项批量删除数据，位置会刷新，不用执行{@link #notifyItemRangeChanged(int, int)}
     *
     * @param data 新数据集
     */
    @Override
    public void notifyNewData(List<T> data) {
        notifyItemRangeRemoved(0, mData.size());
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        notifyItemRangeChanged(0, mData.size());
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
        notifyItemRangeChanged(position, mData.size() - position);
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
        notifyItemRangeChanged(position, mData.size() - position);
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
        notifyItemChanged(position);
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
        notifyItemRemoved(position);
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
        notifyItemRangeRemoved(startPosition, removeList.size());
        mData.removeAll(removeList);
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
        notifyItemMoved(fromPosition, toPosition);
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
        int index = fromPosition;
        fromPosition = Math.min(index, toPosition);
        toPosition = Math.max(index, toPosition);
        notifyItemRangeChanged(fromPosition, Math.abs(toPosition - fromPosition) + 1);
    }

    /**
     * 清空数据集
     */
    @Override
    public void clear() {
        notifyItemRangeRemoved(0, mData.size());
        mData.clear();
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

    /**
     * 注意添加 static ，否则没有Javadoc红色错误，但是在编译时会报“方法不会覆盖或实现超类型的方法”的异常
     *
     * DataBinding可以直接设置控件属性，无需 {@link ViewHelper}
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding = null;

        public RecyclerViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mBinding = dataBinding;
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
