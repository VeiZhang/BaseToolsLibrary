package com.excellence.basetoolslibrary.baseadapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.util.Linkify
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.annotation.*
import androidx.leanback.widget.Visibility
import com.excellence.basetoolslibrary.helper.ViewHelper

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/13
 *     desc   : CommonAdapter控件方法
 * </pre>
 */
class ViewHolder(context: Context, parent: ViewGroup, layoutId: Int) : ViewHelper<ViewHolder> {

    private val mContext: Context
    private val mConvertView: View
    private val mViews: SparseArray<View>

    init {
        mContext = context
        mViews = SparseArray()
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false)
        mConvertView.tag = this
    }

    companion object {

        @JvmStatic
        fun getViewHolder(context: Context, convertView: View?, parent: ViewGroup, layoutId: Int): ViewHolder {
            return convertView?.let { it.tag as ViewHolder }
                    ?: ViewHolder(context, parent, layoutId)
        }

    }

    fun getConvertView(): View {
        return mConvertView
    }

    /**
     * 获取view控件
     *
     * @param viewId 控件资源Id
     * @return view
     */
    fun <T : View> getView(@IdRes viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**** 以下为辅助方法  */

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
    override fun setText(@IdRes viewId: Int, @StringRes strId: Int): ViewHolder {
        val view = getView<TextView>(viewId)!!
        view.setText(strId)
        return this
    }

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    override fun setText(@IdRes viewId: Int, text: String?): ViewHolder {
        val view = getView<TextView>(viewId)!!
        view.text = text
        return this
    }

    /**
     * 设置文本
     *
     * @param viewId 控件资源Id
     * @param text 字符串
     * @return
     */
    override fun setText(@IdRes viewId: Int, text: CharSequence?): ViewHolder {
        val view = getView<TextView>(viewId)!!
        view.text = text
        return this
    }

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColor 颜色资源
     * @return
     */
    override fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): ViewHolder {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(textColor)
        return this
    }

    /**
     * 设置文字颜色
     *
     * @param viewId 控件资源Id
     * @param textColorRes 颜色资源Id
     * @return
     */
    override fun setTextColorRes(@IdRes viewId: Int, @ColorRes textColorRes: Int): ViewHolder {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(mContext.resources.getColor(textColorRes))
        return this
    }

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param resId 图片资源Id
     * @return
     */
    override fun setImageResource(@IdRes viewId: Int, @DrawableRes resId: Int): ViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageResource(resId)
        return this
    }

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param bitmap 位图资源
     * @return
     */
    override fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): ViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageBitmap(bitmap)
        return this
    }

    /**
     * 设置图片
     *
     * @param viewId 控件资源Id
     * @param drawable 图片资源
     * @return
     */
    override fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): ViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageDrawable(drawable)
        return this
    }

    /**
     * 设置背景颜色
     *
     * @param viewId 控件资源Id
     * @param color 背景图片颜色
     * @return
     */
    override fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): ViewHolder {
        val view = getView<View>(viewId)!!
        view.setBackgroundColor(color)
        return this
    }

    /**
     * 设置背景图片
     *
     * @param viewId 控件资源Id
     * @param backgroundRes 背景图片资源Id
     * @return
     */
    override fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): ViewHolder {
        val view = getView<View>(viewId)!!
        view.setBackgroundResource(backgroundRes)
        return this
    }

    /**
     * 设置透明度
     *
     * @param viewId 控件资源Id
     * @param value 透明度
     * @return
     */
    @SuppressLint("NewApi")
    override fun setAlpha(@IdRes viewId: Int, @FloatRange(from = 0.0, to = 1.0) value: Float): ViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId)!!.alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId)!!.startAnimation(alpha)
        }
        return this
    }

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visibility
     *        <ul>
     *          <li>[View.VISIBLE]</li>
     *          <li>[View.INVISIBLE]</li>
     *          <li>[View.GONE]</li>
     *        </ul>
     *
     * @return
     */
    override fun setVisible(@IdRes viewId: Int, @Visibility visibility: Int): ViewHolder {
        val view = getView<View>(viewId)!!
        view.visibility = visibility
        return this
    }

    /**
     * 设置控件是否可见
     *
     * @param viewId 控件资源Id
     * @param visible 是否可见
     * @return
     */
    override fun setVisible(@IdRes viewId: Int, visible: Boolean): ViewHolder {
        val view = getView<View>(viewId)!!
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 设置文本链接
     *
     * @param viewId 控件资源Id
     * @return 超链接
     */
    override fun linkify(@IdRes viewId: Int): ViewHolder {
        val view = getView<TextView>(viewId)!!
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    /**
     * 设置文字字体样式
     *
     * @param viewId 控件资源Id
     * @param typeface 字体样式
     * @return
     */
    override fun setTypeface(viewId: Int, typeface: Typeface?): ViewHolder {
        val view = getView<TextView>(viewId)!!
        view.typeface = typeface
        view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        return this
    }

    /**
     * 设置文字字体样式
     *
     * @param typeface 字体样式
     * @param viewIds 控件资源Ids
     * @return
     */
    override fun setTypeface(typeface: Typeface?, vararg viewIds: Int): ViewHolder {
        for (viewId in viewIds) {
            setTypeface(viewId, typeface)
        }
        return this
    }

    /**
     * 设置进度条进度
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @return
     */
    override fun setProgress(@IdRes viewId: Int, progress: Int): ViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.progress = progress
        return this
    }

    /**
     * 设置进度条进度和最大值
     *
     * @param viewId 控件资源Id
     * @param progress 进度
     * @param max 最大进度
     * @return
     */
    override fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): ViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        view.progress = progress
        return this
    }

    /**
     * 设置进度条最大值
     *
     * @param viewId 控件资源Id
     * @param max 最大值
     * @return
     */
    override fun setMax(@IdRes viewId: Int, max: Int): ViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        return this
    }

    /**
     * 设置评分
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @return
     */
    override fun setRating(@IdRes viewId: Int, rating: Float): ViewHolder {
        val view = getView<RatingBar>(viewId)!!
        view.rating = rating
        return this
    }

    /**
     * 设置评分和最大值
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @param max 最大值
     * @return
     */
    override fun setRating(@IdRes viewId: Int, rating: Float, max: Int): ViewHolder {
        val view = getView<RatingBar>(viewId)!!
        view.max = max
        view.rating = rating
        return this
    }

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param tag 标签
     * @return
     */
    override fun setTag(@IdRes viewId: Int, tag: Any?): ViewHolder {
        val view = getView<View>(viewId)!!
        view.tag = tag
        return this
    }

    /**
     * 设置标签
     *
     * @param viewId 控件资源Id
     * @param key 键值
     * @param tag 标签
     * @return
     */
    override fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): ViewHolder {
        val view = getView<View>(viewId)!!
        view.setTag(key, tag)
        return this
    }

    /**
     * 设置check状态
     *
     * @param viewId 控件资源Id
     * @param checked check状态
     * @return
     */
    override fun setChecked(@IdRes viewId: Int, checked: Boolean): ViewHolder {
        val view = getView<View>(viewId) as Checkable
        view.isChecked = checked
        return this
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
    override fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener?): ViewHolder {
        val view = getView<View>(viewId)!!
        view.setOnClickListener(listener)
        return this
    }

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 触摸事件
     * @return
     */
    override fun setOnTouchListener(@IdRes viewId: Int, listener: OnTouchListener?): ViewHolder {
        val view = getView<View>(viewId)!!
        view.setOnTouchListener(listener)
        return this
    }

    /**
     *
     * @param viewId 控件资源Id
     * @param listener 长按事件
     * @return
     */
    override fun setOnLongClickListener(@IdRes viewId: Int, listener: OnLongClickListener?): ViewHolder {
        val view = getView<View>(viewId)!!
        view.setOnLongClickListener(listener)
        return this
    }
}