package com.excellence.basetoolslibrary.baseadapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by ZhangWei on 2016/6/1.
 */
public abstract class CommonAdapter<T> extends BaseAdapter
{
	private Context mContext = null;
	private List<T> mDatas = null;
	private int mLayoutId;

	/**
	 *
	 * @param context 上下文
	 * @param datas 列表数据源
	 * @param layoutId 布局资源Id
     */
	public CommonAdapter(Context context, List<T> datas, @LayoutRes int layoutId)
	{
		mContext = context;
		mDatas = datas;
		mLayoutId = layoutId;
	}

	/**
	 *
	 * @param context 上下文
	 * @param datas 数组数据源
	 * @param layoutId 布局资源Id
     */
	public CommonAdapter(Context context, T[] datas, @LayoutRes int layoutId)
	{
		mContext = context;
		mDatas = Arrays.asList(datas);
		mLayoutId = layoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas == null ? null : mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, mLayoutId);
		convert(viewHolder, mDatas.get(position), position);
		return viewHolder.getConvertView();
	}

	public abstract void convert(ViewHolder viewHolder, T item, int position);

	/**
	 * 刷新视图
	 *
	 * @param datas 数据源
     */
	public void notifyNewData(List<T> datas)
	{
		mDatas = datas;
		notifyDataSetChanged();
	}
}
