package com.excellence.basetoolslibrary.recycleradapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2016/12/20
 *     desc   : RecyclerView通用适配器
 * </pre>
 */

public abstract class BaseRecyclerAdapter<T> extends MultiItemTypeRecyclerAdapter<T>
{
	private int mLayoutId;

	/**
	 *
	 * @param context 上下文
	 * @param data 数组数据源
	 * @param layoutId 布局资源Id
	 */
	public BaseRecyclerAdapter(Context context, T[] data, @LayoutRes int layoutId)
	{
		this(context, data == null ? null : Arrays.asList(data), layoutId);
	}

	/**
	 *
	 * @param context 上下文
	 * @param data 列表数据源
	 * @param layoutId 布局资源Id
	 */
	public BaseRecyclerAdapter(Context context, List<T> data, @LayoutRes int layoutId)
	{
		super(context, data);
		mLayoutId = layoutId;
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return RecyclerViewHolder.getViewHolder(mContext, parent, mLayoutId);
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		convert(holder, mData.get(position), position);
		setViewListener(holder, position);
	}

	public abstract void convert(RecyclerViewHolder viewHolder, T item, int position);

}
