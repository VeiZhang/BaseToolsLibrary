package com.excellence.basetoolslibrary.utils

import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/13
 *     desc   :
 * </pre>
 */
object ViewUtils {

    /**
     * 只监听一次View绘制完成
     *
     * @param view
     * @param listener
     */
    @JvmStatic
    fun observeViewLayout(view: View?, listener: OnGlobalLayoutListener?) {
        observeViewLayout(view, listener, true)
    }

    /**
     * 一直监听View绘制完成
     *
     * @param view
     * @param listener
     */
    @JvmStatic
    fun observeViewLayoutForever(view: View?, listener: OnGlobalLayoutListener?) {
        observeViewLayout(view, listener, false)
    }

    /**
     * 监听View绘制完成
     *
     * @param view
     * @param listener
     * @param removed 是否移除监听
     */
    @JvmStatic
    fun observeViewLayout(view: View?, listener: OnGlobalLayoutListener?, removed: Boolean) {
        view?.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (removed) {
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                listener?.onGlobalLayout()
            }
        })
    }
}