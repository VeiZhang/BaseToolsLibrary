package com.excellence.basetoolslibrary.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.annotation.FloatRange
import androidx.fragment.app.DialogFragment

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/2
 *     desc   : 透明度相关
 * </pre>
 */
class AlphaUtils {

    companion object {

        /**
         * 设置Window透明度
         */
        @JvmStatic
        fun setAlpha(window: Window?, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            if (window != null) {
                val lp = window.attributes
                lp.alpha = alpha
                window.attributes = lp
            }
        }

        /**
         * 设置Activity的Window透明度
         */
        @JvmStatic
        fun setAlpha(context: Context?, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            if (context is Activity) {
                val window = context.window
                setAlpha(window, alpha)
            }
        }

        /**
         * 设置Dialog的Window透明度
         */
        @JvmStatic
        fun setAlpha(dialog: Dialog?, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            val window = dialog?.window
            setAlpha(window, alpha)
        }

        /**
         * 设置AndroidX-DialogFragment的Window透明度
         */
        @JvmStatic
        fun setAlpha(dialogFragment: DialogFragment?, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            val window = dialogFragment?.dialog?.window
            setAlpha(window, alpha)
        }

        /**
         * 设置DialogFragment的Window透明度
         */
        @JvmStatic
        fun setAlpha(dialogFragment: android.app.DialogFragment?, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            val window = dialogFragment?.dialog?.window
            setAlpha(window, alpha)
        }

    }
}