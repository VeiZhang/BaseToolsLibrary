package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/16
 *     desc   : 开启dataBinding，GridView、ListView通用适配器
 * </pre>
 */

public class CommonBindingAdapter<T> extends MultiItemTypeBindingAdapter<T>
{
	private int mLayoutId;
	private int mVariableId;

	public CommonBindingAdapter(T[] datas, @LayoutRes int layoutId, int variableId)
	{
		this(Arrays.asList(datas), layoutId, variableId);
	}

	public CommonBindingAdapter(List<T> datas, @LayoutRes int layoutId, int variableId)
	{
		super(datas);
		mLayoutId = layoutId;
		mVariableId = variableId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewDataBinding binding;
		if (convertView == null)
			binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
		else
			binding = DataBindingUtil.getBinding(convertView);
		binding.setVariable(mVariableId, getItem(position));
		return binding.getRoot();
	}
}
