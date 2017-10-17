package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/16
 *     desc   : 开启dataBinding，GridView、ListView多布局通用适配器
 * </pre>
 */

public class MultiItemTypeBindingAdapter<T> extends BaseAdapter
{
	protected List<T> mDatas;
	private ItemViewDelegateManager<T> mItemViewDelegateManager;

	public MultiItemTypeBindingAdapter(T[] datas)
	{
		this(datas == null ? null : Arrays.asList(datas));
	}

	public MultiItemTypeBindingAdapter(List<T> datas)
	{
		mDatas = datas;
		mItemViewDelegateManager = new ItemViewDelegateManager<>();
	}

	/**
	 * 添加视图
	 *
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeBindingAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate)
	{
		mItemViewDelegateManager.addDelegate(delegate);
		return this;
	}

	/**
	 * 添加视图
	 *
	 * @param viewType 布局类型
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeBindingAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate)
	{
		mItemViewDelegateManager.addDelegate(viewType, delegate);
		return this;
	}

	/**
	 * 移除视图
	 *
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeBindingAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate)
	{
		mItemViewDelegateManager.removeDelegate(delegate);
		return this;
	}

	/**
	 * 移除视图
	 *
	 * @param viewType 布局类型
	 * @return
	 */
	public MultiItemTypeBindingAdapter<T> removeItemViewDelegate(int viewType)
	{
		mItemViewDelegateManager.removeDelegate(viewType);
		return this;
	}

	/**
	 * 判断视图是否可用
	 *
	 * @return {@code true}:是<br>{@code false}:否
	 */
	private boolean userItemViewDelegateManager()
	{
		return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
	}

	/**
	 * 获取视图数量
	 *
	 * @return 视图数量
	 */
	@Override
	public int getViewTypeCount()
	{
		if (userItemViewDelegateManager())
			return mItemViewDelegateManager.getItemViewDelegateCount();
		return super.getViewTypeCount();
	}

	/**
	 * 获取视图类型
	 *
	 * @param position 位置
	 * @return 视图类型
	 */
	@Override
	public int getItemViewType(int position)
	{
		if (userItemViewDelegateManager())
		{
			return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
		}
		return super.getItemViewType(position);
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
		ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(getItem(position), position);
		ViewDataBinding binding;
		if (convertView == null)
			binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), delegate.getItemViewLayoutId(), parent, false);
		else
			binding = DataBindingUtil.getBinding(convertView);
		binding.setVariable(delegate.getItemVariable(), getItem(position));
		return binding.getRoot();
	}

}
