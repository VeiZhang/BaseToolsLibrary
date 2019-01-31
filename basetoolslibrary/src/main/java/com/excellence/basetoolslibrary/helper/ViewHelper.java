package com.excellence.basetoolslibrary.helper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 适配器控件方法接口
 * </pre>
 */

public interface ViewHelper<T> {

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param strId 字符串资源Id
     * @return
     */
    T setText(@IdRes int viewId, @StringRes int strId);

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    T setText(@IdRes int viewId, String text);

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    T setText(@IdRes int viewId, CharSequence text);

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColor 颜色资源
     * @return
     */
    T setTextColor(@IdRes int viewId, @ColorInt int textColor);

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColorRes 颜色资源Id
     * @return
     */
    T setTextColorRes(@IdRes int viewId, @ColorRes int textColorRes);

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param resId 图片资源Id
     * @return
     */
    T setImageResource(@IdRes int viewId, @DrawableRes int resId);

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param bitmap 位图资源
     * @return
     */
    T setImageBitmap(@IdRes int viewId, Bitmap bitmap);

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param drawable 图片资源
     * @return
     */
    T setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable);

    /**
     * 设置背景颜色
     *
     * @param viewId 控件资源Id
     * @param color 背景图片颜色
     * @return
     */
    T setBackgroundColor(@IdRes int viewId, @ColorInt int color);

    /**
     * 设置背景图片
     *
     * @param viewId 控件资源Id
     * @param backgroundRes 背景图片资源Id
     * @return
     */
    T setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes);

    /**
     * 设置透明度
     *
     * @param viewId 控件资源Id
     * @param value 透明度
     * @return
     */
    @SuppressLint("NewApi")
    T setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float value);

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visibility
     *        <ul>
     *          <li>{@link View#VISIBLE  }</li>
     *          <li>{@link View#INVISIBLE}</li>
     *          <li>{@link View#GONE     }</li>
     *        </ul>
     * @return
     */
    T setVisible(@IdRes int viewId, int visibility);

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visible 是否可见
     * @return
     */
    T setVisible(@IdRes int viewId, boolean visible);

    /**
     * 设置文本链接
     *
     * @param viewId 控件资源Id
     * @return 超链接
     */
    T linkify(@IdRes int viewId);

    /**
     * 设置文字字体样式
     *
     * @param viewId 控件资源Id
     * @param typeface 字体样式
     * @return
     */
    T setTypeface(int viewId, Typeface typeface);

    /**
     * 设置文字字体样式
     *
     * @param typeface 字体样式
     * @param viewIds 控件资源Ids
     * @return
     */
    T setTypeface(Typeface typeface, int... viewIds);

    /**
     * 设置进度条进度
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @return
     */
    T setProgress(@IdRes int viewId, int progress);

    /**
     * 设置进度条进度和最大值
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @param max 最大进度
     * @return
     */
    T setProgress(@IdRes int viewId, int progress, int max);

    /**
     * 设置进度条最大值
     *
     * @param viewId 控件资源Id
     * @param max 最大值
     * @return
     */
    T setMax(@IdRes int viewId, int max);

    /**
     * 设置评分
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @return
     */
    T setRating(@IdRes int viewId, float rating);

    /**
     * 设置评分和最大值
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @param max 最大值
     * @return
     */
    T setRating(@IdRes int viewId, float rating, int max);

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param tag 标签
     * @return
     */
    T setTag(@IdRes int viewId, Object tag);

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param key 键值
     * @param tag 标签
     * @return
     */
    T setTag(@IdRes int viewId, int key, Object tag);

    /**
     * 设置check状态
     *
     * @param viewId 控件资源Id
     * @param checked check状态
     * @return
     */
    T setChecked(@IdRes int viewId, boolean checked);

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 点击事件
     * @return
     */
    T setOnClickListener(@IdRes int viewId, View.OnClickListener listener);

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 触摸事件
     * @return
     */
    T setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener);

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 长按事件
     * @return
     */
    T setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener);
}
