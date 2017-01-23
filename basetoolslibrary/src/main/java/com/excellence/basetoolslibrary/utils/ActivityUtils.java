package com.excellence.basetoolslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by ZhangWei on 2017/1/23.
 */

/**
 * Activity相关
 */
public class ActivityUtils
{
	/**
	 * Activity跳转
	 * 
	 * @param context
	 * @param activityCls
	 */
	public static void startAnotherActivity(Context context, Class<? extends Activity> activityCls)
	{
		Intent intent = new Intent(context, activityCls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 设置Activity窗口透明度
	 * 
	 * @param activity
	 * @param alpha
	 */
	public static void setActivityWindowAlpha(Activity activity, float alpha)
	{
		// TODO Auto-generated method stub
		WindowManager.LayoutParams params = activity.getWindow().getAttributes();
		params.alpha = alpha;
		activity.getWindow().setAttributes(params);
	}
}
