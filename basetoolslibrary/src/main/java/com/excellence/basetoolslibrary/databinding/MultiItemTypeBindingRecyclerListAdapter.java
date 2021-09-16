package com.excellence.basetoolslibrary.databinding;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 开启dataBinding，多种类型布局RecyclerView {@link ListAdapter}通用适配器，继承{@link ListAdapter}，使用内部的Diff
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre>
 */
public class MultiItemTypeBindingRecyclerListAdapter<T> extends ListAdapter<T, RecyclerViewHolder> {

    protected final LifecycleOwner mLifecycleOwner;

    protected final List<T> mData = new ArrayList<>();
    private ItemViewDelegateManager<T> mItemViewDelegateManager = null;
    private OnItemKeyListener mOnItemKeyListener = null;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    private OnItemFocusChangeListener mOnItemFocusChangeListener = null;
    private int mSelectedItemPosition = -1;

    public MultiItemTypeBindingRecyclerListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        this(diffCallback, null);
    }

    public MultiItemTypeBindingRecyclerListAdapter(@NonNull AsyncDifferConfig<T> config) {
        this(config, null);
    }

    public MultiItemTypeBindingRecyclerListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,
                                                   LifecycleOwner lifecycleOwner) {
        super(diffCallback);
        mLifecycleOwner = lifecycleOwner;
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    public MultiItemTypeBindingRecyclerListAdapter(@NonNull AsyncDifferConfig<T> config,
                                                   LifecycleOwner lifecycleOwner) {
        super(config);
        mLifecycleOwner = lifecycleOwner;
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeBindingRecyclerListAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate) {
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
    public MultiItemTypeBindingRecyclerListAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addDelegate(viewType, delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeBindingRecyclerListAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.removeDelegate(delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    public MultiItemTypeBindingRecyclerListAdapter<T> removeItemViewDelegate(int viewType) {
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

    @Override
    public int getItemViewType(int position) {
        if (userItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(getItem(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        holder.markAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerViewHolder holder) {
        holder.markDetachedFromWindow();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return RecyclerViewHolder.getViewHolder(binding, mLifecycleOwner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ItemViewDelegate<T> delegate = getItemViewDelegate(getItemViewType(position));
        ViewDataBinding binding = holder.getBinding();

        T item = getItem(position);
        binding.setVariable(delegate.getItemVariable(), item);
        delegate.convert(binding, item, position);
        binding.executePendingBindings();
        setViewListener(holder, position);
    }

    protected void setViewListener(final RecyclerViewHolder holder, int position) {
        final ViewDataBinding binding = holder.getBinding();
        View itemView = binding.getRoot();

        /**
         * 如果执行了submitList增减，则当监听事件时，position就是错误的
         * 此时应该使用{@link RecyclerViewHolder#getAdapterPosition()} 纠正
         */

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(binding, v, holder.getAdapterPosition());
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mOnItemLongClickListener != null
                        && mOnItemLongClickListener.onItemLongClick(binding, v, holder.getAdapterPosition());
            }
        });

        itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int position = holder.getAdapterPosition();
                mSelectedItemPosition = hasFocus ? position : -1;
                if (mOnItemFocusChangeListener != null && position >= 0) {
                    mOnItemFocusChangeListener.onItemFocusChange(binding, v, hasFocus, position);
                }
            }
        });

        itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return mOnItemKeyListener != null
                        && mOnItemKeyListener.onKey(binding, v, keyCode, event, holder.getAdapterPosition());
            }
        });
    }

    public void setOnItemKeyListener(OnItemKeyListener onItemKeyListener) {
        mOnItemKeyListener = onItemKeyListener;
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

    /**
     * 获取当前焦点位置
     *
     * @return -1表示没有焦点
     */
    public int getSelectedItemPosition() {
        return mSelectedItemPosition;
    }

    @Override
    public void submitList(@Nullable List<T> list) {
        if (isEmpty(list)) {
            /**
             * 当list为空或者size为0时，使用null清空快速
             */
            mData.clear();
            super.submitList(null);
        } else {
            mData.addAll(list);
            super.submitList(list);
        }
    }

    @Nullable
    public List<T> getData() {
        return mData;
    }
}
