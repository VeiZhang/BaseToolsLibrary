package com.excellence.basetoolslibrary.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateManager;
import com.excellence.basetoolslibrary.helper.DataHelper;

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

public class MultiItemTypeBindingAdapter<T> extends BaseAdapter implements DataHelper<T>
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
		{
			return mItemViewDelegateManager.getItemViewDelegateCount();
		}
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
		{
			binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), delegate.getItemViewLayoutId(), parent, false);
		}
		else
		{
			binding = DataBindingUtil.getBinding(convertView);
		}
		binding.setVariable(delegate.getItemVariable(), getItem(position));
		return binding.getRoot();
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
		{
			mDatas.add(position, data);
			notifyDataSetChanged();
		}
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
		{
			mDatas.set(index, newData);
			notifyDataSetChanged();
		}
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
		{
			modify(mDatas.indexOf(oldData), newData);
		}
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
		{
			mDatas.remove(index);
			notifyDataSetChanged();
		}
	}

	/**
	 * 清空数据集
	 */
	@Override
	public void clear()
	{
		if (mDatas != null)
		{
			mDatas.clear();
			notifyDataSetChanged();
		}
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
}
