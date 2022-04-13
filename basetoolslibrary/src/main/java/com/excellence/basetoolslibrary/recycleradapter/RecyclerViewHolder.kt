package com.excellence.basetoolslibrary.recycleradapter

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
import androidx.recyclerview.widget.RecyclerView
import com.excellence.basetoolslibrary.helper.ViewHelper

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2016/12/20
 *     desc   : BaseRecyclerAdapter控件方法
 * </pre>
 */
class RecyclerViewHolder : RecyclerView.ViewHolder, ViewHelper<RecyclerViewHolder?> {

    private var mContext: Context
    private var mConvertView: View
    private var mViews: SparseArray<View?>

    constructor(context: Context, itemView: View) : super(itemView) {
        mContext = context
        mConvertView = itemView
        mViews = SparseArray()
    }

    companion object {
        fun getViewHolder(context: Context, view: View): RecyclerViewHolder {
            return RecyclerViewHolder(context, view)
        }

        fun getViewHolder(context: Context, parent: ViewGroup?, @LayoutRes layoutId: Int): RecyclerViewHolder {
            val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return getViewHolder(context, view)
        }
    }

    fun getConvertView(): View {
        return mConvertView
    }

    /**
     * 获取view控件
     *
     * @param viewId 控件资源Id
     * @return 获取view对象
     */
    fun <T : View?> getView(@IdRes viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }

    /**** 以下为辅助方法 同 [com.excellence.basetoolslibrary.baseadapter.ViewHolder]  */

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
    override fun setText(@IdRes viewId: Int, @StringRes strId: Int): RecyclerViewHolder {
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
    override fun setText(@IdRes viewId: Int, text: String?): RecyclerViewHolder {
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
    override fun setText(@IdRes viewId: Int, text: CharSequence?): RecyclerViewHolder {
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
    override fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): RecyclerViewHolder {
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
    override fun setTextColorRes(@IdRes viewId: Int, @ColorRes textColorRes: Int): RecyclerViewHolder {
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
    override fun setImageResource(@IdRes viewId: Int, @DrawableRes resId: Int): RecyclerViewHolder {
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
    override fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): RecyclerViewHolder {
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
    override fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): RecyclerViewHolder {
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
    override fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): RecyclerViewHolder {
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
    override fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): RecyclerViewHolder {
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
    override fun setAlpha(@IdRes viewId: Int, @FloatRange(from = 0.0, to = 1.0) value: Float): RecyclerViewHolder {
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
    override fun setVisible(@IdRes viewId: Int, @Visibility visibility: Int): RecyclerViewHolder {
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
    override fun setVisible(@IdRes viewId: Int, visible: Boolean): RecyclerViewHolder {
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
    override fun linkify(@IdRes viewId: Int): RecyclerViewHolder {
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
    override fun setTypeface(viewId: Int, typeface: Typeface?): RecyclerViewHolder {
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
    override fun setTypeface(typeface: Typeface?, vararg viewIds: Int): RecyclerViewHolder {
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
    override fun setProgress(@IdRes viewId: Int, progress: Int): RecyclerViewHolder {
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
    override fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): RecyclerViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        view.progress = progress
        return this
    }

    /**
     * 设置进度条最大值
     *
     * @param viewId 控件资源Id
     * @param max 最大进度
     * @return
     */
    override fun setMax(@IdRes viewId: Int, max: Int): RecyclerViewHolder {
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
    override fun setRating(@IdRes viewId: Int, rating: Float): RecyclerViewHolder {
        val view = getView<RatingBar>(viewId)!!
        view.rating = rating
        return this
    }

    /**
     * 设置评分和最大值
     *
     * @param viewId 控件资源Id
     * @param rating 评分
     * @param max 最大分数
     * @return
     */
    override fun setRating(@IdRes viewId: Int, rating: Float, max: Int): RecyclerViewHolder {
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
    override fun setTag(@IdRes viewId: Int, tag: Any?): RecyclerViewHolder {
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
    override fun setTag(@IdRes viewId: Int, key: Int, tag: Any?): RecyclerViewHolder {
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
    override fun setChecked(@IdRes viewId: Int, checked: Boolean): RecyclerViewHolder {
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
    override fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener?): RecyclerViewHolder {
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
    override fun setOnTouchListener(@IdRes viewId: Int, listener: OnTouchListener?): RecyclerViewHolder {
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
    override fun setOnLongClickListener(@IdRes viewId: Int, listener: OnLongClickListener?): RecyclerViewHolder {
        val view = getView<View>(viewId)!!
        view.setOnLongClickListener(listener)
        return this
    }

}