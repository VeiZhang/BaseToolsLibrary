package com.excellence.basetoolslibrary.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/21
 *     desc   : 键盘相关工具类
 * </pre>
 */
object KeyboardUtils {

    /**
     * 避免输入法面板遮挡
     *
     * 在manifest.xml中activity中设置
     * android:windowSoftInputMode="adjustPan"
     */
    /**
     * 动态显示软键盘
     *
     * @param activity activity
     */
    @JvmStatic
    fun showSoftInput(activity: Activity?) {
        if (activity == null) {
            return
        }
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        showSoftInput(view)
    }

    /**
     * 动态显示软键盘
     *
     * @param view 视图
     */
    @JvmStatic
    fun showSoftInput(view: View?) {
        if (view == null) {
            return
        }
        val imm = getInputMethodManager(view.context)
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    @JvmStatic
    fun hideSoftInput(activity: Activity?) {
        if (activity == null) {
            return
        }
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        hideSoftInput(view)
    }

    /**
     * 动态隐藏软键盘
     *
     * @param view 视图
     */
    @JvmStatic
    fun hideSoftInput(view: View?) {
        if (view == null) {
            return
        }
        val imm = getInputMethodManager(view.context)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 隐藏软键盘，可能不准确
     *
     * @param context
     */
    @JvmStatic
    fun hideSoftInput(context: Context) {
        val imm = getInputMethodManager(context)
        if (imm.isActive) {
            return
        }
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 打开软键盘，可能不准确
     *
     * @param context
     */
    @JvmStatic
    fun showSoftInput(context: Context) {
        val imm = getInputMethodManager(context)
        if (!imm.isActive) {
            return
        }
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 如果输入法在窗口上已经显示，则隐藏，反之则显示
     *
     * @param context
     */
    @JvmStatic
    fun toggleSoftInput(context: Context) {
        val imm = getInputMethodManager(context)
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    @JvmStatic
    fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     *
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     * 需重写dispatchTouchEvent
     * 参照以下注释代码
     */
    @JvmStatic
    fun clickBlankArea2HideSoftInput() {
        Log.d("tips", "U should copy the following code.")

/*		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
		    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
		        View v = getCurrentFocus();
		        if (isShouldHideKeyboard(v, ev)) {
		            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		        }
		    }
		    return super.dispatchTouchEvent(ev);
		}
		// 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
		private boolean isShouldHideKeyboard(View v, MotionEvent event) {
		    if (v != null && (v instanceof EditText)) {
		        int[] l = {0, 0};
		        v.getLocationInWindow(l);
		        int left = l[0],
		                top = l[1],
		                bottom = top + v.getHeight(),
		                right = left + v.getWidth();
		        return !(event.getX() > left && event.getX() < right
		                && event.getY() > top && event.getY() < bottom);
		    }
		    return false;
		}*/
    }
}