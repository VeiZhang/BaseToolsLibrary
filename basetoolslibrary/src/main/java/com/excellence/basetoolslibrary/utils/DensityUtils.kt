package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * <pre>
 * author : VeiZhang
 * blog   : https://veizhang.github.io/
 * time   : 2017/1/23
 * desc   : 分辨率相关工具类
 * </pre>
 */
object DensityUtils {

    /**
     * 获取当前屏幕分辨率
     *
     * @param context 上下文
     * @return 分辨率
     */
    @JvmStatic
    fun getDensity(context: Context): Float {
        val dm = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)

        // context.getResources().getDisplayMetrics().density;
        return dm.density
    }

    /**
     * 获取当前文字分辨率
     *
     * @param context 上下文
     * @return 分辨率
     */
    @JvmStatic
    fun getScaleDensity(context: Context): Float =
            context.resources.displayMetrics.scaledDensity

    /**
     * 获取屏幕宽度 @Deprecated 使用 [getScreenSize] 替代该方法
     *
     * @param context 上下文
     * @return
     */
    @JvmStatic
    @Deprecated("")
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    /**
     * 获取屏幕高度 @Deprecated 使用 [getScreenSize] 替代该方法
     *
     * @param context 上下文
     * @return
     */
    @JvmStatic
    @Deprecated("")
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }

    /**
     * 获取屏幕宽、高
     *
     * @param context 上下文
     * @return
     */
    @JvmStatic
    fun getScreenSize(context: Context): Point {
        val point = Point()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getSize(point)
        return point
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp
     * @return px
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Int): Int =
            (dpValue * getDensity(context) + 0.5f).toInt()


    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue px
     * @return dp
     */
    @JvmStatic
    fun px2dp(context: Context, pxValue: Float): Int =
            (pxValue / getDensity(context) + 0.5f).toInt()

    /**
     * sp转px
     *
     * @param context 上下文
     * @param spValue sp
     * @return px
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int =
            (spValue * getScaleDensity(context) + 0.5f).toInt()

    /**
     * px转sp
     *
     * @param context 上下文
     * @param pxValue px
     * @return sp
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int =
            (pxValue / getScaleDensity(context) + 0.5f).toInt()

}