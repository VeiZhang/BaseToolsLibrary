package com.excellence.basetoolslibrary.baseadapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2016/6/1
 *     desc   : ListView、GridView通用适配器
 * </pre>
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    private int mLayoutId;

    /**
     *
     * @param data 数组数据源
     * @param layoutId 布局资源Id
     */
    public CommonAdapter(T[] data, @LayoutRes int layoutId) {
        this(data == null ? null : Arrays.asList(data), layoutId);
    }

    /**
     *
     * @param data 列表数据源
     * @param layoutId 布局资源Id
     */
    public CommonAdapter(List<T> data, @LayoutRes int layoutId) {
        super(data);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(parent.getContext(), convertView, parent, mLayoutId);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, T item, int position);

}
