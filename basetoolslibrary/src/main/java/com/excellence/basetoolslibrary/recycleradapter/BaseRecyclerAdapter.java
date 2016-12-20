package com.excellence.basetoolslibrary.recycleradapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhangWei on 2016/12/20.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
{
	private Context mContext = null;
	private List<T> mDatas = null;
	private int mLayoutId;
	private OnItemClickListener mOnItemClickListener = null;

	public BaseRecyclerAdapter(Context context, List<T> datas, int layoutId)
	{
		mContext = context;
		mDatas = datas;
		mLayoutId = layoutId;
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		RecyclerViewHolder viewHolder = RecyclerViewHolder.getViewHolder(mContext, parent, mLayoutId);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		convert(holder, mDatas.get(position), position);
		setViewListener(holder, position);
	}

	private void setViewListener(final RecyclerViewHolder viewHolder, final int position)
	{
		viewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mOnItemClickListener != null)
					mOnItemClickListener.onItemClick(viewHolder, v, position);
			}
		});

		viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				return mOnItemClickListener != null && mOnItemClickListener.onItemLongClick(viewHolder, v, position);
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return mDatas == null ? 0 : mDatas.size();
	}

	public void notifyNewData(List<T> datas)
	{
		mDatas = datas;
		notifyDataSetChanged();
	}

	public interface OnItemClickListener
	{
		void onItemClick(RecyclerViewHolder viewHolder, View v, int position);

		boolean onItemLongClick(RecyclerViewHolder viewHolder, View v, int position);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		mOnItemClickListener = onItemClickListener;
	}

	public abstract void convert(RecyclerViewHolder viewHolder, T item, int position);

}
