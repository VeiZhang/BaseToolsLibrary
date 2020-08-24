package com.excellence.basetoolslibrary.recycleradapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 单类型布局RecyclerView {@link ListAdapter}通用适配器，继承{@link ListAdapter}，使用内部的Diff
 * </pre> 
 */
public abstract class BaseRecyclerListAdapter<T> extends MultiItemTypeRecyclerListAdapter<T> {

    private int mLayoutId;

    public BaseRecyclerListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback, @LayoutRes int layoutId) {
        super(diffCallback);
        mLayoutId = layoutId;
    }

    public BaseRecyclerListAdapter(@NonNull AsyncDifferConfig<T> config, @LayoutRes int layoutId) {
        super(config);
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecyclerViewHolder.getViewHolder(parent.getContext(), parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        /**
         * 1.重写该方法，position正确，但是{@link #setViewListener(RecyclerViewHolder, Object)}要传入Item，而不是position
         *
         * 2.当submitList改变列表时，监听事件里面的position不对，需要纠正，
         * 可以用 {@link RecyclerView#getChildAdapterPosition(View)}
         *
         * 为了纠正position，不使用提供的position，而使用{@link List#indexOf(Object)}
         */
        T item = getItem(position);
        convert(holder, item, position);
        setViewListener(holder, item);
    }

    public abstract void convert(RecyclerViewHolder viewHolder, T item, int position);

}
