package com.excellence.basetoolslibrary.baseadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegateManager;
import com.excellence.basetoolslibrary.helper.DataHelper;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : 多种类型GridView ListView通用适配器
 * </pre>
 */

public class MultiItemTypeAdapter<T> extends BaseAdapter implements DataHelper<T>
{
	protected List<T> mData;
	private ItemViewDelegateManager<T> mItemViewDelegateManager;

	public MultiItemTypeAdapter(T[] data)
	{
		this(data == null ? null : Arrays.asList(data));
	}

	public MultiItemTypeAdapter(List<T> data)
	{
		mData = data;
		mItemViewDelegateManager = new ItemViewDelegateManager<>();
	}

	/**
	 * 添加视图
	 *
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate)
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
	public MultiItemTypeAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate)
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
	public MultiItemTypeAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate)
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
	public MultiItemTypeAdapter<T> removeItemViewDelegate(int viewType)
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
			return mItemViewDelegateManager.getItemViewType(mData.get(position), position);
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getCount()
	{
		return mData == null ? 0 : mData.size();
	}

	@Override
	public T getItem(int position)
	{
		return mData == null ? null : mData.get(position);
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
		int layoutId = delegate.getItemViewLayoutId();
		ViewHolder viewHolder = ViewHolder.getViewHolder(parent.getContext(), convertView, parent, layoutId);
		delegate.convert(viewHolder, getItem(position), position);
		return viewHolder.getConvertView();
	}

	/**
	 * 新数据集替代旧数据集，刷新视图
	 *
	 * @param data 新数据集
	 */
	@Override
	public void notifyNewData(List<T> data)
	{
		mData = data;
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
		boolean result = mData != null && mData.addAll(list);
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
		boolean result = mData != null && mData.addAll(position, list);
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
		boolean result = mData != null && mData.add(data);
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
		if (mData != null)
		{
			mData.add(position, data);
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
		if (mData != null)
		{
			mData.set(index, newData);
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
		if (mData != null)
		{
			modify(mData.indexOf(oldData), newData);
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
		boolean result = mData != null && mData.remove(data);
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
		if (mData != null)
		{
			mData.remove(index);
			notifyDataSetChanged();
		}
	}

	/**
	 * 清空数据集
	 */
	@Override
	public void clear()
	{
		if (mData != null)
		{
			mData.clear();
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
		return mData != null && mData.contains(data);
	}
}
