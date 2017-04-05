package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/23
 *     desc   : 分辨率相关工具类
 * </pre>
 */

public class DensityUtils
{
	/**
	 * 获取当前屏幕分辨率
	 *
	 * @param context 上下文
	 * @return 分辨率
	 */
	public static float getDensity(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

		// context.getResources().getDisplayMetrics().density;

		return dm.density;
	}

	/**
	 * 获取当前文字分辨率
	 *
	 * @param context 上下文
	 * @return 分辨率
	 */
	public static float getScaleDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().scaledDensity;
	}

	/**
	 * dp转px
	 *
	 * @param context 上下文
	 * @param dpValue dp
	 * @return px
	 */
	public static int dp2px(Context context, int dpValue)
	{
		return (int) (dpValue * getDensity(context) + 0.5f);
	}

	/**
	 * px转dp
	 *
	 * @param context 上下文
	 * @param pxValue px
	 * @return dp
	 */
	public static int px2dp(Context context, float pxValue)
	{
		return (int) (pxValue / getDensity(context) + 0.5f);
	}

	/**
	 * sp转px
	 *
	 * @param context 上下文
	 * @param spValue sp
	 * @return px
	 */
	public static int sp2px(Context context, float spValue)
	{
		return (int) (spValue * getScaleDensity(context) + 0.5f);
	}

	/**
	 * px转sp
	 *
	 * @param context 上下文
	 * @param pxValue px
	 * @return sp
	 */
	public static int px2sp(Context context, float pxValue)
	{
		return (int) (pxValue / getScaleDensity(context) + 0.5f);
	}
}
