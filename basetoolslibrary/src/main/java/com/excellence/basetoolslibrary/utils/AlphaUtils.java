package com.excellence.basetoolslibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/9/20
 *     desc   : 透明度相关
 * </pre> 
 */
public class AlphaUtils {

    /**
     * 设置Window透明度
     *
     * @param window
     * @param alpha
     */
    public static void setAlpha(Window window, float alpha) {
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = alpha;
            window.setAttributes(lp);
        }
    }

    /**
     * 设置Activity的Window透明度
     *
     * @param context
     * @param alpha
     */
    public static void setAlpha(Context context, float alpha) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            setAlpha(window, alpha);
        }
    }

    /**
     * 设置Dialog的Window透明度
     *
     * @param dialog
     * @param alpha
     */
    public static void setAlpha(Dialog dialog, float alpha) {
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        setAlpha(window, alpha);
    }

    /**
     * 设置DialogFragment的Window透明度
     *
     * @param dialogFragment
     * @param alpha
     */
    public static void setAlpha(DialogFragment dialogFragment, float alpha) {
        if (dialogFragment == null) {
            return;
        }
        Window window = dialogFragment.getDialog().getWindow();
        setAlpha(window, alpha);
    }

    /**
     * 设置DialogFragment的Window透明度
     *
     * @param dialogFragment
     * @param alpha
     */
    public static void setAlpha(androidx.fragment.app.DialogFragment dialogFragment, float alpha) {
        if (dialogFragment == null) {
            return;
        }
        Window window = dialogFragment.getDialog().getWindow();
        setAlpha(window, alpha);
    }
}
