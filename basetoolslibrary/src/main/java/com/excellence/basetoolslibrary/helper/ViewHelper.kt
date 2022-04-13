package com.excellence.basetoolslibrary.helper

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import androidx.annotation.*

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 适配器控件方法接口
 * </pre>
 */
interface ViewHelper<T> {
    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param strId 字符串资源Id
     * @return
     */
    fun setText(@IdRes viewId: Int, @StringRes strId: Int): T

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    fun setText(@IdRes viewId: Int, text: String?): T

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    fun setText(@IdRes viewId: Int, text: CharSequence?): T

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColor 颜色资源
     * @return
     */
    fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): T

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColorRes 颜色资源Id
     * @return
     */
    fun setTextColorRes(@IdRes viewId: Int, @ColorRes textColorRes: Int): T

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param resId 图片资源Id
     * @return
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes resId: Int): T

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param bitmap 位图资源
     * @return
     */
    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): T

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param drawable 图片资源
     * @return
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): T

    /**
     * 设置背景颜色
     *
     * @param viewId 控件资源Id
     * @param color 背景图片颜色
     * @return
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): T

    /**
     * 设置背景图片
     *
     * @param viewId 控件资源Id
     * @param backgroundRes 背景图片资源Id
     * @return
     */
    fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): T

    /**
     * 设置透明度
     *
     * @param viewId 控件资源Id
     * @param value 透明度
     * @return
     */
    @SuppressLint("NewApi")
    fun setAlpha(@IdRes viewId: Int, @FloatRange(from = 0.0, to = 1.0) value: Float): T

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visibility
     *
     *  * [View.VISIBLE]
     *  * [View.INVISIBLE]
     *  * [View.GONE]
     *
     * @return
     */
    fun setVisible(@IdRes viewId: Int, visibility: Int): T

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visible 是否可见
     * @return
     */
    fun setVisible(@IdRes viewId: Int, visible: Boolean): T

    /**
     * 设置文本链接
     *
     * @param viewId 控件资源Id
     * @return 超链接
     */
    fun linkify(@IdRes viewId: Int): T

    /**
     * 设置文字字体样式
     *
     * @param viewId 控件资源Id
     * @param typeface 字体样式
     * @return
     */
    fun setTypeface(viewId: Int, typeface: Typeface?): T

    /**
     * 设置文字字体样式
     *
     * @param typeface 字体样式
     * @param viewIds 控件资源Ids
     * @return
     */
    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): T

    /**
     * 设置进度条进度
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @return
     */
    fun setProgress(@IdRes viewId: Int, progress: Int): T

    /**
     * 设置进度条进度和最大值
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @param max 最大进度
     * @return
     */
    fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): T

    /**
     * 设置进度条最大值
     *
     * @param viewId 控件资源Id
     * @param max 最大值
     * @return
     */
    fun setMax(@IdRes viewId: Int, max: Int): T

    /**
     * 设置评分
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @return
     */
    fun setRating(@IdRes viewId: Int, rating: Float): T

    /**
     * 设置评分和最大值
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @param max 最大值
     * @return
     */
    fun setRating(@IdRes viewId: Int, rating: Float, max: Int): T

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param tag 标签
     * @return
     */
    fun setTag(@IdRes viewId: Int, tag: Any?): T

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param key 键值
     * @param tag 标签
     * @return
     */
    fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): T

    /**
     * 设置check状态
     *
     * @param viewId 控件资源Id
     * @param checked check状态
     * @return
     */
    fun setChecked(@IdRes viewId: Int, checked: Boolean): T

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 点击事件
     * @return
     */
    fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener?): T

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 触摸事件
     * @return
     */
    fun setOnTouchListener(@IdRes viewId: Int, listener: OnTouchListener?): T

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 长按事件
     * @return
     */
    fun setOnLongClickListener(@IdRes viewId: Int, listener: OnLongClickListener?): T
}
