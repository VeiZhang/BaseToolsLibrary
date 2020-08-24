package com.excellence.basetoolslibrary.databinding;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;

import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 开启dataBinding，多种类型布局RecyclerView {@link ListAdapter}通用适配器，继承{@link ListAdapter}，使用内部的Diff
 * </pre>
 */
public abstract class MultiItemTypeBindingRecyclerListAdapter<T> extends ListAdapter<T, RecyclerViewHolder> {

    private ItemViewDelegateManager<T> mItemViewDelegateManager = null;
    private OnItemKeyListener mOnItemKeyListener = null;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    private OnItemFocusChangeListener mOnItemFocusChangeListener = null;
    private int mSelectedItemPosition = -1;
    private List<T> mList;

    public MultiItemTypeBindingRecyclerListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    public MultiItemTypeBindingRecyclerListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
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

    public int getItemPosition(T item) {
        return mList == null ? -1 : mList.indexOf(item);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ItemViewDelegate<T> delegate = getItemViewDelegate(getItemViewType(position));
        ViewDataBinding binding = holder.getBinding();
        /**
         * 1.重写该方法时，position正确，但是{@link #setViewListener(ViewDataBinding, Object)}要传入Item，而不是position
         *
         * 2.当submitList改变列表时，监听事件里面的position不对，需要纠正，
         * 可以用 {@link RecyclerView#getChildAdapterPosition(View)}
         *
         * 为了纠正position，不使用提供的position，而使用{@link List#indexOf(Object)}
         */
        T item = getItem(position);
        binding.setVariable(delegate.getItemVariable(), item);
        delegate.convert(binding, item, position);
        binding.executePendingBindings();
        setViewListener(binding, item);
    }

    @CallSuper
    protected void setViewListener(final ViewDataBinding binding, final T item) {
        View itemView = binding.getRoot();

        /**
         * 为了纠正position，不使用提供的position，而使用{@link List#indexOf(Object)}
         */

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(binding, v, getItemPosition(item));
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mOnItemLongClickListener != null
                        && mOnItemLongClickListener.onItemLongClick(binding, v, getItemPosition(item));
            }
        });

        itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int position = getItemPosition(item);
                mSelectedItemPosition = hasFocus ? position : -1;
                if (mOnItemFocusChangeListener != null) {
                    mOnItemFocusChangeListener.onItemFocusChange(binding, v, hasFocus, position);
                }
            }
        });

        itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return mOnItemKeyListener != null
                        && mOnItemKeyListener.onKey(binding, v, keyCode, event, getItemPosition(item));
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
            mList = null;
            super.submitList(null);
        } else {
            mList = list;
            super.submitList(list);
        }
    }

}
