package com.excellence.basetoolslibrary.pageradapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/21
 *     desc   : viewpager通用适配器：分页加载
 * </pre>
 */

public abstract class BasePagerAdapter extends PagerAdapter
{
	private SparseArray<View> mViews = null;
	private int mPageCount = 0;

	/**
	 * paging load
	 */
	public BasePagerAdapter()
	{
		this(0);
	}

	/**
	 * paging load
	 *
	 * @param pageCount total page count
	 */
	public BasePagerAdapter(int pageCount)
	{
		mViews = new SparseArray<>();
		setData(pageCount);
	}

	public void setData(int pageCount)
	{
		mViews.clear();
		mPageCount = pageCount;
	}

	@Override
	public int getCount()
	{
		return mPageCount > 0 ? mPageCount : 0;
	}

	/**
	 * 一定要重写，否则刷新不成功
	 *
	 * @param object
	 * @return
	 */
	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		View view = mViews.get(position);
		if (view == null)
		{
			view = loadView(container.getContext(), position);
			mViews.put(position, view);
		}
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView((View) object);
	}

	protected abstract View loadView(Context context, int pageIndex);

	public void notifyNewData(int pageCount)
	{
		setData(pageCount);
		notifyDataSetChanged();
	}
}
