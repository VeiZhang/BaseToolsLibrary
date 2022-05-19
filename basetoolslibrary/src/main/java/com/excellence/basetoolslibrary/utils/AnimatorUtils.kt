package com.excellence.basetoolslibrary.utils

import android.animation.*
import android.view.View
import android.view.ViewGroup.MarginLayoutParams

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/18
 *     desc   : 属性动画
 * </pre>
 */
object AnimatorUtils {

    private const val ANIM_DURATION = 200
    private const val SCALE = 1.2f
    private const val SCALE_X = "scaleX"
    private const val SCALE_Y = "scaleY"
    private const val TRANSLATION_X = "translationX"
    private const val TRANSLATION_Y = "translationY"
    private const val ALPHA = "alpha"

    /**
     * FrameLayout里面相邻的两个子View，进行Margin的平移，达到背景不移动，View移动的效果
     * title显示，content右移
     * 不能通过[android.view.animation.TranslateAnimation]，会把背景也移动
     *
     * @param titleView
     * @param contentView
     * @param duration
     */
    @JvmStatic
    fun marginLeftTranslateIn(titleView: View, contentView: View, duration: Int) {
        marginTranslateIn(titleView, contentView, duration, View.FOCUS_LEFT)
    }

    /**
     * FrameLayout里面相邻的两个子View，进行Margin的平移，达到背景不移动，View移动的效果
     * title隐藏，content全屏展示
     * 不能通过[android.view.animation.TranslateAnimation]，会把背景也移动
     *
     * @param titleView
     * @param contentView
     * @param duration
     */
    @JvmStatic
    fun marginLeftTranslateOut(titleView: View, contentView: View, duration: Int) {
        marginTranslateOut(titleView, contentView, duration, View.FOCUS_LEFT)
    }

    /**
     * FrameLayout里面相邻的两个子View，进行Margin的平移，达到背景不移动，View移动的效果
     * title显示，content右移
     * 不能通过[android.view.animation.TranslateAnimation]，会把背景也移动
     *
     * @param titleView
     * @param contentView
     * @param duration
     */
    @JvmStatic
    fun marginUpTranslateIn(titleView: View, contentView: View, duration: Int) {
        marginTranslateIn(titleView, contentView, duration, View.FOCUS_UP)
    }

    /**
     * FrameLayout里面相邻的两个子View，进行Margin的平移，达到背景不移动，View移动的效果
     * title隐藏，content全屏展示
     * 不能通过[android.view.animation.TranslateAnimation]，会把背景也移动
     *
     * @param titleView
     * @param contentView
     * @param duration
     */
    @JvmStatic
    fun marginUpTranslateOut(titleView: View, contentView: View, duration: Int) {
        marginTranslateOut(titleView, contentView, duration, View.FOCUS_UP)
    }

    /**
     * FrameLayout里面相邻的两个子View，进行Margin的平移，达到背景不移动，View移动的效果
     * title显示，content右移，同时让title可见，为了处理焦点
     * 不能通过[android.view.animation.TranslateAnimation]，会把背景也移动
     *
     * @param titleView
     * @param contentView
     * @param duration
     * @param direction [View.FOCUS_LEFT]
     * [View.FOCUS_UP]
     * [View.FOCUS_RIGHT]
     * [View.FOCUS_DOWN]
     */
    @JvmStatic
    fun marginTranslateIn(titleView: View, contentView: View, duration: Int, direction: Int) {
        val titleLayoutParams = titleView.layoutParams as MarginLayoutParams
        val contentLayoutParams = contentView.layoutParams as MarginLayoutParams
        var titleMargin = 0
        var contentMargin = 0
        var start = 0
        var end = 0
        var initValue = 0
        when (direction) {
            View.FOCUS_LEFT -> {
                titleMargin = titleLayoutParams.leftMargin
                contentMargin = contentLayoutParams.leftMargin
                start = titleMargin
                end = 0
                initValue = -titleMargin
            }
            View.FOCUS_UP -> {
                titleMargin = titleLayoutParams.topMargin
                contentMargin = contentLayoutParams.topMargin
                start = titleMargin
                end = 0
                initValue = -titleMargin
            }
            View.FOCUS_RIGHT -> {
                titleMargin = titleLayoutParams.rightMargin
                contentMargin = contentLayoutParams.rightMargin
                start = titleMargin
                end = 0
                initValue = -titleMargin
            }
            View.FOCUS_DOWN -> {
                titleMargin = titleLayoutParams.bottomMargin
                contentMargin = contentLayoutParams.bottomMargin
                start = titleMargin
                end = 0
                initValue = -titleMargin
            }
            else -> return
        }
        val finalTitleMargin = titleMargin
        val finalContentMargin = contentMargin
        val finalInitValue = initValue

        /**
         * -200 -> 0
         */
        val animator = ObjectAnimator.ofInt(start, end)
        animator.addUpdateListener { animation: ValueAnimator ->
            val value = finalInitValue + animation.animatedValue as Int
            when (direction) {
                View.FOCUS_LEFT -> {
                    titleLayoutParams.leftMargin = finalTitleMargin + value
                    contentLayoutParams.leftMargin = finalContentMargin + value
                }
                View.FOCUS_UP -> {
                    titleLayoutParams.topMargin = finalTitleMargin + value
                    contentLayoutParams.topMargin = finalContentMargin + value
                }
                View.FOCUS_RIGHT -> {
                    titleLayoutParams.rightMargin = finalTitleMargin + value
                    contentLayoutParams.rightMargin = finalContentMargin + value
                }
                View.FOCUS_DOWN -> {
                    titleLayoutParams.bottomMargin = finalTitleMargin + value
                    contentLayoutParams.bottomMargin = finalContentMargin + value
                }
                else -> return@addUpdateListener
            }
            titleView.layoutParams = titleLayoutParams
            contentView.layoutParams = contentLayoutParams
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override
            fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                titleView.visibility = View.VISIBLE
            }
        })
        animator.duration = duration.toLong()
        animator.start()
    }

    /**
     * FrameLayout里面相邻的两个子View，进行Margin的平移，达到背景不移动，View移动的效果
     * title隐藏，content全屏展示，同时让title不可见，为了处理焦点
     * 不能通过[android.view.animation.TranslateAnimation]，会把背景也移动
     *
     * @param titleView
     * @param contentView
     * @param duration
     * @param direction [View.FOCUS_LEFT]
     * [View.FOCUS_UP]
     * [View.FOCUS_RIGHT]
     * [View.FOCUS_DOWN]
     */
    @JvmStatic
    fun marginTranslateOut(titleView: View, contentView: View, duration: Int, direction: Int) {
        val titleLayoutParams = titleView.layoutParams as MarginLayoutParams
        val contentLayoutParams = contentView.layoutParams as MarginLayoutParams
        var titleMargin = 0
        var contentMargin = 0
        var start = 0
        var end = 0
        when (direction) {
            View.FOCUS_LEFT -> {
                titleMargin = titleLayoutParams.leftMargin
                contentMargin = contentLayoutParams.leftMargin
                start = titleMargin
                end = -titleView.width
            }
            View.FOCUS_UP -> {
                titleMargin = titleLayoutParams.topMargin
                contentMargin = contentLayoutParams.topMargin
                start = titleMargin
                end = -titleView.height
            }
            View.FOCUS_RIGHT -> {
                titleMargin = titleLayoutParams.rightMargin
                contentMargin = contentLayoutParams.rightMargin
                start = titleMargin
                end = -titleView.width
            }
            View.FOCUS_DOWN -> {
                titleMargin = titleLayoutParams.bottomMargin
                contentMargin = contentLayoutParams.bottomMargin
                start = titleMargin
                end = -titleView.height
            }
            else -> return
        }
        val finalTitleMargin = titleMargin
        val finalContentMargin = contentMargin

        /**
         * 0 -> -200
         */
        val animator = ObjectAnimator.ofInt(start, end)
        animator.addUpdateListener { animation: ValueAnimator ->
            val value = animation.animatedValue as Int
            when (direction) {
                View.FOCUS_LEFT -> {
                    titleLayoutParams.leftMargin = finalTitleMargin + value
                    /***
                     * 存在titleview 测量的宽度和实际配置的leftMargin不等，导致contentview左边有部分缺失
                     * 手动矫正，确保全部显示
                     */
                    var contentLeftMargin = finalContentMargin + value
                    if (contentLeftMargin < 0) {
                        contentLeftMargin = 0
                    }
                    contentLayoutParams.leftMargin = contentLeftMargin
                }
                View.FOCUS_UP -> {
                    titleLayoutParams.topMargin = finalTitleMargin + value
                    contentLayoutParams.topMargin = finalContentMargin + value
                }
                View.FOCUS_RIGHT -> {
                    titleLayoutParams.rightMargin = finalTitleMargin + value
                    /***
                     * 存在titleview 测量的宽度和实际配置的rightMargin不等，导致contentview左边有部分缺失
                     * 手动矫正，确保全部显示
                     */
                    var contentRightMargin = finalContentMargin + value
                    if (contentRightMargin < 0) {
                        contentRightMargin = 0
                    }
                    contentLayoutParams.rightMargin = contentRightMargin
                }
                View.FOCUS_DOWN -> {
                    titleLayoutParams.bottomMargin = finalTitleMargin + value
                    contentLayoutParams.bottomMargin = finalContentMargin + value
                }
                else -> return@addUpdateListener
            }
            titleView.layoutParams = titleLayoutParams
            contentView.layoutParams = contentLayoutParams
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override
            fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                titleView.visibility = View.GONE
            }
        })
        animator.duration = duration.toLong()
        animator.start()
    }

    /**
     * 在原基础上，移动View的外边距
     *
     * @param view
     * @param direction
     * @param margin
     */
    @JvmStatic
    fun marginByLayout(view: View, direction: Int, margin: Int) {
        val params = view.layoutParams as MarginLayoutParams
        when (direction) {
            View.FOCUS_LEFT -> params.leftMargin += margin
            View.FOCUS_UP -> params.topMargin += margin
            View.FOCUS_RIGHT -> params.rightMargin += margin
            View.FOCUS_DOWN -> params.bottomMargin += margin
            else -> return
        }
        view.layoutParams = params
    }

    /**
     * 设置View的外边距
     *
     * @param view
     * @param direction
     * @param margin
     */
    @JvmStatic
    fun marginToLayout(view: View, direction: Int, margin: Int) {
        val params = view.layoutParams as MarginLayoutParams
        when (direction) {
            View.FOCUS_LEFT -> params.leftMargin = margin
            View.FOCUS_UP -> params.topMargin = margin
            View.FOCUS_RIGHT -> params.rightMargin = margin
            View.FOCUS_DOWN -> params.bottomMargin = margin
            else -> return
        }
        view.layoutParams = params
    }

    /**
     * 变化View的高度动画
     */
    @JvmOverloads
    @JvmStatic
    fun heightToLayout(view: View,
                       fromH: Int = 0,
                       toH: Int = view.height,
                       duration: Long = ANIM_DURATION.toLong()) {
        val animator = ObjectAnimator.ofInt(fromH, toH)
        animator.addUpdateListener { animation: ValueAnimator ->
            val value = animation.animatedValue as Int
            val params = view.layoutParams
            params.height = value
            view.layoutParams = params
        }
        animator.duration = duration
        animator.start()
    }

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
            val animX = ObjectAnimator.ofFloat(view, SCALE_X, fromX, toX)
            val animY = ObjectAnimator.ofFloat(view, SCALE_Y, fromY, toY)
            val animatorSet = AnimatorSet()
            animatorSet.play(animX).with(animY)
            animatorSet.duration = duration
            animatorSet.start()
        } else {
            val animX = ObjectAnimator.ofFloat(view, SCALE_X, toX, fromX)
            val animY = ObjectAnimator.ofFloat(view, SCALE_Y, toY, fromY)
            val animatorSet = AnimatorSet()
            animatorSet.play(animX).with(animY)
            animatorSet.duration = duration
            animatorSet.start()
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
            val animX = ObjectAnimator.ofFloat(view, TRANSLATION_X, fromX, toX)
            val animY = ObjectAnimator.ofFloat(view, TRANSLATION_Y, fromY, toY)
            val animatorSet = AnimatorSet()
            animatorSet.play(animX).with(animY)
            animatorSet.duration = duration
            animatorSet.start()
        } else {
            val animX = ObjectAnimator.ofFloat(view, TRANSLATION_X, toX, fromX)
            val animY = ObjectAnimator.ofFloat(view, TRANSLATION_Y, toY, fromY)
            val animatorSet = AnimatorSet()
            animatorSet.play(animX).with(animY)
            animatorSet.duration = duration
            animatorSet.start()
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
            val animX = ObjectAnimator.ofFloat(view, ALPHA, from, to)
            animX.duration = duration
            animX.start()
        } else {
            val animX = ObjectAnimator.ofFloat(view, ALPHA, to, from)
            animX.duration = duration
            animX.start()
        }
    }
}