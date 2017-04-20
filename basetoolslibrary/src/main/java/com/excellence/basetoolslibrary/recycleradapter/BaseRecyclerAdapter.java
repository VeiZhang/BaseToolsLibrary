package com.excellence.basetoolslibrary.recycleradapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
	 * @param datas 数组数据源
	 * @param layoutId 布局资源Id
	 */
	public BaseRecyclerAdapter(Context context, T[] datas, @LayoutRes int layoutId)
	{
		this(context, datas == null ? null : Arrays.asList(datas), layoutId);
	}

	/**
	 *
	 * @param context 上下文
	 * @param datas 列表数据源
	 * @param layoutId 布局资源Id
	 */
	public BaseRecyclerAdapter(Context context, List<T> datas, @LayoutRes int layoutId)
	{
		super(context, datas);
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
		convert(holder, mDatas.get(position), position);
		setViewListener(holder, position);
	}

	public abstract void convert(RecyclerViewHolder viewHolder, T item, int position);

}
