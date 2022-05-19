package com.excellence.basetoolslibrary.utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/18
 *     desc   : 补间动画
 * </pre>
 */
object AnimationUtils {

    private const val ANIM_DURATION = 200
    private const val SCALE = 1.2f

    /**
     * 放大缩小动画
     *
     * @param view
     * @param isScaled
     */
    @JvmOverloads
    @JvmStatic
    fun scaleView(view: View, isScaled: Boolean,
                  fromX: Float = 1F,
                  fromY: Float = 1F,
                  toX: Float = SCALE,
                  toY: Float = SCALE,
                  duration: Long = ANIM_DURATION.toLong()) {
        if (isScaled) {
            val animation = ScaleAnimation(fromX, toX, fromY, toY,
                    RELATIVE_TO_SELF, 0F,
                    RELATIVE_TO_SELF, 0F)
            animation.duration = duration
            view.startAnimation(animation)
        } else {
            val animation = ScaleAnimation(toX, fromX, toY, fromY,
                    RELATIVE_TO_SELF, 0F,
                    RELATIVE_TO_SELF, 0F)
            animation.duration = duration
            view.startAnimation(animation)
        }
    }

    /**
     * 平移动画
     */
    @JvmOverloads
    @JvmStatic
    fun translateView(view: View, translate: Boolean,
                      fromX: Float = 0F,
                      fromY: Float = 0F,
                      toX: Float,
                      toY: Float,
                      duration: Long = ANIM_DURATION.toLong()) {
        if (translate) {
            val animation = TranslateAnimation(fromX, toX, fromY, toY)
            animation.duration = duration
            view.startAnimation(animation)
        } else {
            val animation = TranslateAnimation(fromX, toX, fromY, toY)
            animation.duration = duration
            view.startAnimation(animation)
        }
    }

    /**
     * 透明度动画
     */
    @JvmOverloads
    @JvmStatic
    fun alphaView(view: View, alpha: Boolean,
                  from: Float = 1F,
                  to: Float,
                  duration: Long = ANIM_DURATION.toLong()) {
        if (alpha) {
            val animation = AlphaAnimation(from, to)
            animation.duration = duration
            view.startAnimation(animation)
        } else {
            val animation = AlphaAnimation(to, from)
            animation.duration = duration
            view.startAnimation(animation)
        }
    }

}