package com.excellence.basetoolslibrary.helper;

import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 适配器数据方法接口
 * </pre>
 */

public interface DataHelper<T>
{
	T getItem(int position);

	/**
	 * 新数据集替代旧数据集，刷新视图
	 *
	 * @param data 新数据集
	 */
	void notifyNewData(List<T> data);

	/**
	 * 新增数据集
	 *
	 * @param list 新数据集
	 * @return {@code true}:添加成功<br>{@code false}:添加失败
	 */
	boolean addAll(List<T> list);

	/**
	 * 插入新数据集
	 *
	 * @param position 插入位置
	 * @param list 新数据集
	 * @return {@code true}:添加成功<br>{@code false}:添加失败
	 */
	boolean addAll(int position, List<T> list);

	/**
	 * 新增数据
	 *
	 * @param data 数据
	 * @return {@code true}:添加成功<br>{@code false}:添加失败
	 */
	boolean add(T data);

	/**
	 * 插入新数据
	 *
	 * @param position 插入位置
	 * @param data 数据
	 */
	void add(int position, T data);

	/**
	 * 替换数据
	 *
	 * @param index 替换位置
	 * @param newData 替换数据
	 */
	void modify(int index, T newData);

	/**
	 * 替换数据
	 *
	 * @param oldData 被替换数据
	 * @param newData 替换数据
	 */
	void modify(T oldData, T newData);

	/**
	 * 删除数据
	 *
	 * @param data 被删除数据
	 * @return {@code true}:删除成功<br>{@code false}:删除失败
	 */
	boolean remove(T data);

	/**
	 * 删除数据
	 *
	 * @param index 删除位置
	 */
	void remove(int index);

	/**
	 * 清空数据集
	 */
	void clear();

	/**
	 * 判断数据集是否包含数据
	 *
	 * @param data 待检测数据
	 * @return {@code true}:包含<br>{@code false}: 不包含
	 */
	boolean contains(T data);
}
