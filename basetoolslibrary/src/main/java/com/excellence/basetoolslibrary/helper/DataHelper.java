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

public interface DataHelper<T> {

    /**
     * 获取数据集
     *
     * @return
     */
    List<T> getData();

    /**
     * 获取单个数据
     *
     * @param position
     * @return
     */
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
     * @param data 新数据集
     * @return {@code true}:添加成功<br>{@code false}:添加失败
     */
    void addAll(List<T> data);

    /**
     * 插入新数据集
     *
     * @param position 插入位置
     * @param data 新数据集
     * @return {@code true}:添加成功<br>{@code false}:添加失败
     */
    void addAll(int position, List<T> data);

    /**
     * 新增数据
     *
     * @param item 数据
     * @return {@code true}:添加成功<br>{@code false}:添加失败
     */
    void add(T item);

    /**
     * 插入新数据
     *
     * @param position 插入位置
     * @param item 数据
     */
    void add(int position, T item);

    /**
     * 修改源数据
     *
     * @param item 数据集中的对象，修改复杂类型（非基本类型）里面的变量值
     */
    void modify(T item);

    /**
     * 替换数据
     *
     * @param position 替换位置
     * @param item 替换数据
     */
    void modify(int position, T item);

    /**
     * 替换数据
     *
     * @param oldItem 被替换数据
     * @param newItem 替换数据
     */
    void modify(T oldItem, T newItem);

    /**
     * 删除数据
     *
     * @param item 被删除数据
     * @return {@code true}:删除成功<br>{@code false}:删除失败
     */
    void remove(T item);

    /**
     * 删除数据
     *
     * @param position 删除位置
     */
    void remove(int position);

    /**
     * 批量删除
     *
     * @param startPosition 起始位置
     * @param endPosition 结束位置
     */
    void remove(int startPosition, int endPosition);

    /**
     * 交换位置，fromPosition与toPosition交换
     * 1 2 3 4 -> 1 4 3 2
     *
     * @param fromPosition
     * @param toPosition
     */
    void swap(int fromPosition, int toPosition);

    /**
     * 移动位置，从fromPosition插入到toPosition
     * 1 2 3 4 -> 1 3 4 2
     *
     * @param fromPosition
     * @param toPosition
     */
    void move(int fromPosition, int toPosition);

    /**
     * 清空数据集
     */
    void clear();

    /**
     * 判断数据集是否包含数据
     *
     * @param item 待检测数据
     * @return {@code true}:包含<br>{@code false}: 不包含
     */
    boolean contains(T item);
}
