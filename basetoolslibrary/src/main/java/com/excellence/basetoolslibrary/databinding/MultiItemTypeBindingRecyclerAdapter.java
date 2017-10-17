package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter.RecyclerViewHolder;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;
import com.excellence.basetoolslibrary.helper.DataHelper;

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

public class MultiItemTypeBindingRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements DataHelper<T>
{
	protected List<T> mDatas = null;
	private ItemViewDelegateManager<T> mItemViewDelegateManager = null;
	private OnItemClickListener mOnItemClickListener = null;

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
		setViewListener(binding, position);
	}

	protected void setViewListener(final ViewDataBinding binding, final int position)
	{
		binding.getRoot().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mOnItemClickListener != null)
					mOnItemClickListener.onItemClick(binding, v, position);
			}
		});

		binding.getRoot().setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				return mOnItemClickListener != null && mOnItemClickListener.onItemLongClick(binding, v, position);
			}
		});
	}

	public interface OnItemClickListener
	{
		void onItemClick(ViewDataBinding binding, View v, int position);

		boolean onItemLongClick(ViewDataBinding binding, View v, int position);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		mOnItemClickListener = onItemClickListener;
	}

	/**
	 * 新数据集替代旧数据集，刷新视图
	 *
	 * @param datas 新数据集
	 */
	@Override
	public void notifyNewData(List<T> datas)
	{
		mDatas = datas;
		notifyDataSetChanged();
	}

	/**
	 * 新增数据集
	 *
	 * @param list 新数据集
	 * @return {@code true}:添加成功<br>{@code false}:添加失败
	 */
	@Override
	public boolean addAll(List<T> list)
	{
		boolean result = mDatas != null && mDatas.addAll(list);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * 插入新数据集
	 *
	 * @param position 插入位置
	 * @param list 新数据集
	 * @return {@code true}:添加成功<br>{@code false}:添加失败
	 */
	@Override
	public boolean addAll(int position, List<T> list)
	{
		boolean result = mDatas != null && mDatas.addAll(position, list);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * 新增数据
	 *
	 * @param data 数据
	 * @return {@code true}:添加成功<br>{@code false}:添加失败
	 */
	@Override
	public boolean add(T data)
	{
		boolean result = mDatas != null && mDatas.add(data);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * 插入新数据
	 *
	 * @param position 插入位置
	 * @param data 数据
	 */
	@Override
	public void add(int position, T data)
	{
		if (mDatas != null)
			mDatas.add(position, data);
		notifyDataSetChanged();
	}

	/**
	 * 替换数据
	 *
	 * @param index 替换位置
	 * @param newData 替换数据
	 */
	@Override
	public void modify(int index, T newData)
	{
		if (mDatas != null)
			mDatas.set(index, newData);
		notifyDataSetChanged();
	}

	/**
	 * 替换数据
	 *
	 * @param oldData 被替换数据
	 * @param newData 替换数据
	 */
	@Override
	public void modify(T oldData, T newData)
	{
		if (mDatas != null)
			modify(mDatas.indexOf(oldData), newData);
	}

	/**
	 * 删除数据
	 *
	 * @param data 被删除数据
	 * @return {@code true}:删除成功<br>{@code false}:删除失败
	 */
	@Override
	public boolean remove(T data)
	{
		boolean result = mDatas != null && mDatas.remove(data);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * 删除数据
	 *
	 * @param index 删除位置
	 */
	@Override
	public void remove(int index)
	{
		if (mDatas != null)
			mDatas.remove(index);
		notifyDataSetChanged();
	}

	/**
	 * 清空数据集
	 */
	@Override
	public void clear()
	{
		if (mDatas != null)
			mDatas.clear();
		notifyDataSetChanged();
	}

	/**
	 * 判断数据集是否包含数据
	 *
	 * @param data 待检测数据
	 * @return {@code true}:包含<br>{@code false}: 不包含
	 */
	@Override
	public boolean contains(T data)
	{
		return mDatas != null && mDatas.contains(data);
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
