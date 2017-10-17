package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   : 开启dataBinding，RecyclerView通用适配器
 * </pre>
 */

public class BaseRecyclerBindingAdapter<T> extends MultiItemTypeBindingRecyclerAdapter<T>
{
	private int mLayoutId;
	private int mVariableId;

	public BaseRecyclerBindingAdapter(T[] datas, @LayoutRes int layoutId, int variableId)
	{
		this(Arrays.asList(datas), layoutId, variableId);
	}

	public BaseRecyclerBindingAdapter(List<T> datas, @LayoutRes int layoutId, int variableId)
	{
		super(datas);
		mLayoutId = layoutId;
		mVariableId = variableId;
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
		return new RecyclerViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		ViewDataBinding binding = holder.getBinding();
		binding.setVariable(mVariableId, mDatas.get(position));
		binding.executePendingBindings();
	}
}
