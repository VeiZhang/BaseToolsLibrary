package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter.RecyclerViewHolder;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   : 开启dataBinding，多种类型布局RecyclerView通用适配器
 * </pre>
 */

public class MultiItemTypeBindingRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
{
	protected List<T> mDatas = null;
	private ItemViewDelegateManager<T> mItemViewDelegateManager = null;

	public MultiItemTypeBindingRecyclerAdapter(T[] datas)
	{
		this(datas == null ? null : Arrays.asList(datas));
	}

	public MultiItemTypeBindingRecyclerAdapter(List<T> datas)
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
	public MultiItemTypeBindingRecyclerAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate)
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
	public MultiItemTypeBindingRecyclerAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate)
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
	public MultiItemTypeBindingRecyclerAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate)
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
	public MultiItemTypeBindingRecyclerAdapter<T> removeItemViewDelegate(int viewType)
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

	@Override
	public int getItemViewType(int position)
	{
		if (userItemViewDelegateManager())
			return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
		return super.getItemViewType(position);
	}

	@Override
	public int getItemCount()
	{
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
		ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
		return new RecyclerViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(getItemViewType(position));
		ViewDataBinding binding = holder.getBinding();
		binding.setVariable(delegate.getItemVariable(), mDatas.get(position));
		binding.executePendingBindings();
	}

	/**
	 * 注意添加 static ，否则没有Javadoc红色错误，但是在编译时会报“方法不会覆盖或实现超类型的方法”的异常
	 */
	static class RecyclerViewHolder extends RecyclerView.ViewHolder
	{
		private ViewDataBinding mBinding = null;

		public RecyclerViewHolder(ViewDataBinding dataBinding)
		{
			super(dataBinding.getRoot());
			mBinding = dataBinding;
		}

		public ViewDataBinding getBinding()
		{
			return mBinding;
		}
	}
}
