package com.excellence.basetoolslibrary.baseadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.excellence.basetoolslibrary.helper.ViewHelper;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2016/6/1
 *     desc   : CommonAdapter控件方法
 * </pre>
 */

public class ViewHolder implements ViewHelper<ViewHolder> {

    private Context mContext = null;
    private View mConvertView = null;
    private SparseArray<View> mViews = null;

    public ViewHolder(Context context, ViewGroup parent, int layoutId) {
        mContext = context;
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 获取view控件
     *
     * @param viewId 控件资源Id
     * @return view
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**** 以下为辅助方法 *****/

    /**
     * 关于属性
     */

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param strId 字符串资源Id
     * @return
     */
    @Override
    public ViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    @Override
    public ViewHolder setText(@IdRes int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    @Override
    public ViewHolder setText(@IdRes int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColor 颜色资源
     * @return
     */
    @Override
    public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColorRes 颜色资源Id
     * @return
     */
    @Override
    public ViewHolder setTextColorRes(@IdRes int viewId, @ColorRes int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param resId 图片资源Id
     * @return
     */
    @Override
    public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param bitmap 位图资源
     * @return
     */
    @Override
    public ViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param drawable 图片资源
     * @return
     */
    @Override
    public ViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param viewId 控件资源Id
     * @param color 背景图片颜色
     * @return
     */
    @Override
    public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置背景图片
     *
     * @param viewId 控件资源Id
     * @param backgroundRes 背景图片资源Id
     * @return
     */
    @Override
    public ViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * 设置透明度
     *
     * @param viewId 控件资源Id
     * @param value 透明度
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public ViewHolder setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

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
    @Override
    public ViewHolder setVisible(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visible 是否可见
     * @return
     */
    @Override
    public ViewHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置文本链接
     *
     * @param viewId 控件资源Id
     * @return 超链接
     */
    @Override
    public ViewHolder linkify(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 设置文字字体样式
     *
     * @param viewId 控件资源Id
     * @param typeface 字体样式
     * @return
     */
    @Override
    public ViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * 设置文字字体样式
     *
     * @param typeface 字体样式
     * @param viewIds 控件资源Ids
     * @return
     */
    @Override
    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            setTypeface(viewId, typeface);
        }
        return this;
    }

    /**
     * 设置进度条进度
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @return
     */
    @Override
    public ViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * 设置进度条进度和最大值
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @param max 最大进度
     * @return
     */
    @Override
    public ViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * 设置进度条最大值
     *
     * @param viewId 控件资源Id
     * @param max 最大值
     * @return
     */
    @Override
    public ViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * 设置评分
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @return
     */
    @Override
    public ViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置评分和最大值
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @param max 最大值
     * @return
     */
    @Override
    public ViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param tag 标签
     * @return
     */
    @Override
    public ViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param key 键值
     * @param tag 标签
     * @return
     */
    @Override
    public ViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * 设置check状态
     *
     * @param viewId 控件资源Id
     * @param checked check状态
     * @return
     */
    @Override
    public ViewHolder setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 点击事件
     * @return
     */
    @Override
    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 触摸事件
     * @return
     */
    @Override
    public ViewHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 长按事件
     * @return
     */
    @Override
    public ViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
