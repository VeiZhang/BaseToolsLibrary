package com.excellence.basetoolslibrary.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.helper.DataHelper;
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegateManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/20
 *     desc   : 多种类型布局RecyclerView通用适配器
 * </pre>
 */

public class MultiItemTypeRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements DataHelper<T>
{
	protected List<T> mData = new ArrayList<>();
	private ItemViewDelegateManager<T> mItemViewDelegateManager = null;
	private OnItemClickListener mOnItemClickListener = null;
	private OnItemLongClickListener mOnItemLongClickListener = null;
	private OnItemFocusChangeListener mOnItemFocusChangeListener = null;
	private OnItemKeyListener mOnItemKeyListener = null;

	public MultiItemTypeRecyclerAdapter(T[] data)
	{
		this(data == null ? null : Arrays.asList(data));
	}

	public MultiItemTypeRecyclerAdapter(List<T> data)
	{
		mData.addAll(data);
		mItemViewDelegateManager = new ItemViewDelegateManager<>();
	}

	/**
	 * 添加视图
	 *
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeRecyclerAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate)
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
	public MultiItemTypeRecyclerAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate)
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
	public MultiItemTypeRecyclerAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate)
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
	public MultiItemTypeRecyclerAdapter<T> removeItemViewDelegate(int viewType)
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
		{
			return mItemViewDelegateManager.getItemViewType(mData.get(position), position);
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getItemCount()
	{
		return mData == null ? 0 : mData.size();
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
		return RecyclerViewHolder.getViewHolder(parent.getContext(), parent, layoutId);
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(getItemViewType(position));
		delegate.convert(holder, mData.get(position), position);
		setViewListener(holder, position);
	}

	protected void setViewListener(final RecyclerViewHolder viewHolder, final int position)
	{
		View itemView = viewHolder.getConvertView();
		itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mOnItemClickListener != null)
				{
					mOnItemClickListener.onItemClick(viewHolder, v, position);
				}
			}
		});

		itemView.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				return mOnItemLongClickListener != null && mOnItemLongClickListener.onItemLongClick(viewHolder, v, position);
			}
		});

		itemView.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (mOnItemFocusChangeListener != null)
				{
					mOnItemFocusChangeListener.onItemFocusChange(viewHolder, v, hasFocus, position);
				}
			}
		});

		itemView.setOnKeyListener(new View.OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				return mOnItemKeyListener != null && mOnItemKeyListener.onKey(viewHolder, v, keyCode, event, position);
			}
		});
	}

	public interface OnItemClickListener
	{
		void onItemClick(RecyclerViewHolder viewHolder, View v, int position);
	}

	public interface OnItemLongClickListener
	{
		boolean onItemLongClick(RecyclerViewHolder viewHolder, View v, int position);
	}

	public interface OnItemFocusChangeListener
	{
		void onItemFocusChange(RecyclerViewHolder viewHolder, View v, boolean hasFocus, int position);
	}

	public interface OnItemKeyListener
	{
		boolean onKey(RecyclerViewHolder viewHolder, View v, int keyCode, KeyEvent event, int position);
	}

	public void setOnItemClickListener(OnItemClickListener listener)
	{
		mOnItemClickListener = listener;
	}

	public void setOnItemLongClickListener(OnItemLongClickListener listener)
	{
		mOnItemLongClickListener = listener;
	}

	public void setOnItemFocusChangeListener(OnItemFocusChangeListener listener)
	{
		mOnItemFocusChangeListener = listener;
	}

	public void setOnItemKeyListener(OnItemKeyListener onItemKeyListener)
	{
		mOnItemKeyListener = onItemKeyListener;
	}

	/**** 以下为辅助方法 ****/

	@Override
	public T getItem(int position)
	{
		return mData == null ? null : mData.get(position);
	}

	/**
	 * 新数据集替代旧数据集，刷新视图
	 *
	 * @param data 新数据集
	 */
	@Override
	public void notifyNewData(List<T> data)
	{
		mData.clear();
		mData.addAll(data);
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
