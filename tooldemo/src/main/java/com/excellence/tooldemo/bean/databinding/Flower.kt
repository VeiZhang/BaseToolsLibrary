package com.excellence.tooldemo.bean.databinding

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.excellence.tooldemo.BR

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   :
 * </pre>
 */
open class Flower(name: String?, @DrawableRes iconRes: Int) : BaseObservable() {

    @get:Bindable
    var name: String? = null
    var iconRes: Int

    fun onImageClick(view: View?) {
        val flag = "[点击]"
        name = if (name!!.contains(flag)) name!!.replace(flag, "") else name + flag
        notifyPropertyChanged(BR.name)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("img")
        fun loadImg(imageView: ImageView, @DrawableRes resId: Int) {
            imageView.setImageResource(resId)
        }
    }

    init {
        this.name = name
        this.iconRes = iconRes
    }
}