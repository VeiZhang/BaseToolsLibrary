package com.excellence.basetoolslibrary.recycleradapter;

import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.LayoutRes;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2016/12/20
 *     desc   : RecyclerView通用适配器
 * </pre>
 */

public abstract class BaseRecyclerAdapter<T> extends MultiItemTypeRecyclerAdapter<T> {

    private int mLayoutId;

    /**
     *
     * @param data 数组数据源
     * @param layoutId 布局资源Id
     */
    public BaseRecyclerAdapter(T[] data, @LayoutRes int layoutId) {
        this(data == null ? null : Arrays.asList(data), layoutId);
    }

    /**
     *
     * @param data 列表数据源
     * @param layoutId 布局资源Id
     */
    public BaseRecyclerAdapter(List<T> data, @LayoutRes int layoutId) {
        super(data);
        mLayoutId = layoutId;
    }

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
