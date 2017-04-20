package com.excellence.basetoolslibrary.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegateManager;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/20
 *     desc   : 多布局RecyclerView通用适配器
 * </pre>
 */

public class MultiItemTypeRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
{
	private Context mContext = null;
	private List<T> mDatas = null;
	private ItemViewDelegateManager<T> mItemViewDelegateManager = null;

	public MultiItemTypeRecyclerAdapter(Context context, T[] datas)
	{
		this(context, datas == null ? null : Arrays.asList(datas));
	}

	public MultiItemTypeRecyclerAdapter(Context context, List<T> datas)
	{
		mContext = context;
		mDatas = datas;
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
		return RecyclerViewHolder.getViewHolder(mContext, parent, layoutId);
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position)
	{
		ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(getItemViewType(position));
		delegate.convert(holder, mDatas.get(position), position);
	}
}
