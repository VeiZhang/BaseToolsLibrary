package com.excellence.basetoolslibrary.recycleradapter;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegateManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 多种类型布局RecyclerView {@link ListAdapter}通用适配器
 * </pre> 
 */
public abstract class MultiItemTypeRecyclerListAdapter<T> extends ListAdapter<T, RecyclerViewHolder> {

    private ItemViewDelegateManager<T> mItemViewDelegateManager = null;
    private OnItemKeyListener mOnItemKeyListener = null;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    private OnItemFocusChangeListener mOnItemFocusChangeListener = null;
    private int mSelectedItemPosition = -1;

    public MultiItemTypeRecyclerListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    public MultiItemTypeRecyclerListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    /**
     * 添加视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeRecyclerListAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate) {
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
    public MultiItemTypeRecyclerListAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addDelegate(viewType, delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param delegate 视图
     * @return
     */
    public MultiItemTypeRecyclerListAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.removeDelegate(delegate);
        return this;
    }

    /**
     * 移除视图
     *
     * @param viewType 布局类型
     * @return
     */
    public MultiItemTypeRecyclerListAdapter<T> removeItemViewDelegate(int viewType) {
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

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
        return RecyclerViewHolder.getViewHolder(parent.getContext(), parent, layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ItemViewDelegate<T> delegate = getItemViewDelegate(getItemViewType(position));

        delegate.convert(holder, getItem(position), position);
        setViewListener(holder, position);
    }

    protected void setViewListener(final RecyclerViewHolder viewHolder, int position) {
        View itemView = viewHolder.getConvertView();

        /**
         * 如果执行了submitList增减，则当监听事件时，position就是错误的
         * 此时应该使用{@link RecyclerViewHolder#getAdapterPosition()} 纠正
         */

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(viewHolder, v, viewHolder.getAdapterPosition());
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mOnItemLongClickListener != null
                        && mOnItemLongClickListener.onItemLongClick(viewHolder, v, viewHolder.getAdapterPosition());
            }
        });

        itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int position = viewHolder.getAdapterPosition();
                mSelectedItemPosition = hasFocus ? position : -1;
                if (mOnItemFocusChangeListener != null) {
                    mOnItemFocusChangeListener.onItemFocusChange(viewHolder, v, hasFocus, position);
                }
            }
        });

        itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return mOnItemKeyListener != null
                        && mOnItemKeyListener.onKey(viewHolder, v, keyCode, event, viewHolder.getAdapterPosition());
            }
        });
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

    @Override
    public void submitList(@Nullable List<T> list) {
        if (isEmpty(list)) {
            /**
             * 当list为空或者size为0时，使用null清空快速
             */
            super.submitList(null);
        } else {
            super.submitList(list);
        }
    }

}
