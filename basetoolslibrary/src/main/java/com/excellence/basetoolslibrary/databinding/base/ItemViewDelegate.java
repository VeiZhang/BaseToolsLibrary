package com.excellence.basetoolslibrary.databinding.base;

import android.support.annotation.LayoutRes;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/16
 *     desc   : {@link com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingAdapter}
 *     			{@link com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter}
 *              多布局视图接口
 *     			多布局使用时，多组数据集成基类，方便于判断是否使用特定视图接口
 * </pre>
 */

public interface ItemViewDelegate<T>
{
	/**
	 * 布局资源Id
	 *
	 * @return 布局Id
	 */
	@LayoutRes
	int getItemViewLayoutId();

	/**
	 * ViewDataBinding的设置项{@link android.databinding.ViewDataBinding#setVariable(int, Object)}
	 *
	 * @return variableId
	 */
	int getItemVariable();

	/**
	 * 判断视图是否使用该类布局
	 *
	 * @param item 数据
	 * @param position 位置
	 * @return {@code true}:是<br>{@code false}:否
	 */
	boolean isForViewType(T item, int position);

}
