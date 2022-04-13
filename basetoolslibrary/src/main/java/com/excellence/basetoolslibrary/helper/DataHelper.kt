package com.excellence.basetoolslibrary.helper

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 适配器数据方法接口
 * </pre>
 */
interface DataHelper<T> {
    /**
     * 获取数据集
     *
     * @return
     */
    val data: List<T?>

    /**
     * 获取单个数据
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T?

    /**
     * 新数据集替代旧数据集，刷新视图
     *
     * @param data 新数据集
     */
    fun notifyNewData(data: List<T>?)

    /**
     * 新增数据集
     *
     * @param data 新数据集
     * @return `true`:添加成功<br>`false`:添加失败
     */
    fun addAll(data: List<T>?)

    /**
     * 插入新数据集
     *
     * @param position 插入位置
     * @param data 新数据集
     * @return `true`:添加成功<br>`false`:添加失败
     */
    fun addAll(position: Int, data: List<T>?)

    /**
     * 新增数据
     *
     * @param item 数据
     * @return `true`:添加成功<br>`false`:添加失败
     */
    fun add(item: T?)

    /**
     * 插入新数据
     *
     * @param position 插入位置
     * @param item 数据
     */
    fun add(position: Int, item: T?)

    /**
     * 修改源数据
     *
     * @param item 数据集中的对象，修改复杂类型（非基本类型）里面的变量值
     */
    fun modify(item: T?)

    /**
     * 替换数据
     *
     * @param position 替换位置
     * @param item 替换数据
     */
    fun modify(position: Int, item: T?)

    /**
     * 替换数据
     *
     * @param oldItem 被替换数据
     * @param newItem 替换数据
     */
    fun modify(oldItem: T?, newItem: T?)

    /**
     * 删除数据
     *
     * @param item 被删除数据
     * @return `true`:删除成功<br>`false`:删除失败
     */
    fun remove(item: T?)

    /**
     * 删除数据
     *
     * @param position 删除位置
     */
    fun remove(position: Int)

    /**
     * 批量删除
     *
     * @param startPosition 起始位置
     * @param endPosition 结束位置
     */
    fun remove(startPosition: Int, endPosition: Int)

    /**
     * 交换位置，fromPosition与toPosition交换
     * 1 2 3 4 -> 1 4 3 2
     *
     * @param fromPosition
     * @param toPosition
     */
    fun swap(fromPosition: Int, toPosition: Int)

    /**
     * 移动位置，从fromPosition插入到toPosition
     * 1 2 3 4 -> 1 3 4 2
     *
     * @param fromPosition
     * @param toPosition
     */
    fun move(fromPosition: Int, toPosition: Int)

    /**
     * 清空数据集
     */
    fun clear()

    /**
     * 判断数据集是否包含数据
     *
     * @param item 待检测数据
     * @return `true`:包含<br>`false`: 不包含
     */
    operator fun contains(item: T?): Boolean
}