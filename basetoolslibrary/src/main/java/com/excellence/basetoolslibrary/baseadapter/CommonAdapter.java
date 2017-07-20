package com.excellence.basetoolslibrary.baseadapter;

import android.content.Context;
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

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{
	private int mLayoutId;

	/**
	 *
	 * @param context 上下文
	 * @param datas 数组数据源
	 * @param layoutId 布局资源Id
	 */
	public CommonAdapter(Context context, T[] datas, @LayoutRes int layoutId)
	{
		this(context, datas == null ? null : Arrays.asList(datas), layoutId);
	}

	/**
	 *
	 * @param context 上下文
	 * @param datas 列表数据源
	 * @param layoutId 布局资源Id
	 */
	public CommonAdapter(Context context, List<T> datas, @LayoutRes int layoutId)
	{
		super(context, datas);
		mLayoutId = layoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, mLayoutId);
		convert(viewHolder, mDatas.get(position), position);
		return viewHolder.getConvertView();
	}

	public abstract void convert(ViewHolder viewHolder, T item, int position);

}
