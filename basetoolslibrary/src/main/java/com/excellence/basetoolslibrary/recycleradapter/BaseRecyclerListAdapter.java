package com.excellence.basetoolslibrary.recycleradapter;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   :
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
        convert(holder, getItem(position), position);
        setViewListener(holder, position);
    }

    public abstract void convert(RecyclerViewHolder viewHolder, T item, int position);

}
