package com.excellence.basetoolslibrary.pageradapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/21
 *     desc   : viewpager通用适配器：分页加载
 * </pre>
 */
abstract class BasePagerAdapter : PagerAdapter {

    private val mViews: SparseArray<View> = SparseArray()
    private var mPageCount = 0

    /**
     * paging load
     *
     * @param pageCount total page count
     */
    @JvmOverloads
    constructor(pageCount: Int = 0) {
        setData(pageCount)
    }

    fun setData(pageCount: Int) {
        mViews.clear()
        mPageCount = pageCount
    }

    override fun getCount(): Int {
        return if (mPageCount > 0) mPageCount else 0
    }

    /**
     * 一定要重写，否则刷新不成功
     *
     * @param object
     * @return
     */
    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = mViews[position]
        if (view == null) {
            view = loadView(container.context, position)
            mViews.put(position, view)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    protected abstract fun loadView(context: Context, pageIndex: Int): View

    fun notifyNewData(pageCount: Int) {
        setData(pageCount)
        notifyDataSetChanged()
    }

}